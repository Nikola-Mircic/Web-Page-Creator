package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.nm.wpc.editor.option.ContinueRecentOption;
import com.nm.wpc.editor.option.NewProjectOption;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;

public class StartScreen extends Screen{
	private static final long serialVersionUID = 1L;
	private MainScreen ms;
	
	private GUIObject last;
	private InputField editing;
	
	private Option options[];
	
	public StartScreen(int w,int h,MainScreen ms) {
		super(w, h);
		this.ms = ms;
		
		this.options = new Option[3];
		options[0] =  new NewProjectOption(this.ms);
		options[1] =  new NewProjectOption(this.ms);
		options[2] =  new ContinueRecentOption(this.ms);
		
		addButtons();
		
		this.drawContent(w,h);
	}
	
	@Override
	public void drawContent(int width,int height) {
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		for(GUIObject object:objects) {
			g.drawImage((Image)object.getImg(), object.getX(), object.getY(), null);
		}
		for(InputPanel panel : panels) {
			g.drawImage(panel.getContent(), panel.getX(), panel.getY(), null);
			panel.drawChildPanels(g);
		}
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	private void addButtons() {
		Button btn;
		InputField input;
		for(int i=0;i<3;i++) {
			btn = (Button)(new Button(100, 100+i*30, 50, 19, options[i]).setContainer(this));
			btn.fitText(1);
			objects.add(btn);
		}
		input = (InputField)(new InputField("Test",100, 260, 150, 50,1).setContainer(this));
		objects.add(input);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		panelsActivity = false;
		InputPanel temp = null;
		editing = findEditingField();
		
		if(editing!= null) {
			editing.setEditing(false);
			editing = null;
		}
		
		//Test if clicked on any open panel
		for (InputPanel panel : panels) {
			int x1 = panel.getX();
			int y1 =  panel.getY();
			int x2 = x1+panel.getW();
			int y2 = y1+panel.getH();
			if(x1<x && x<x2 && y1<y && y<y2) {
				//Check panels stored in this class
				panelsActivity = true;
				temp = panel;
			}else {
				//For each panel check it's child panels
				temp = panel.getClicked(x, y);
				if(temp != null) {
					panelsActivity = true;
					break;
				}
			}
		}
		
		if(panelsActivity) {
			//If user clicked on a panel,activate it's function
			temp.onMousePressed(x, y);
			this.drawContent(getW(), getH());
			return;
		}else {
			//No panels clicked,close all;
			this.panels.removeAll(panels);
		}
		
		for(GUIObject object:objects) {
			//GUIObject check
			if(x>=object.getX() && x<=object.getX()+object.getWidth() && y>=object.getY() && y<=object.getY()+object.getHeight()) {
				if(object instanceof InputField) {
					//if object is instance od InputField,send it x and y to check if clicked on editable part of input field
					object.mousePressed(x, y);
					editing = (InputField)object;
				}else {
					object.mousePressed();
					last = object;
				}
				break;
			}
		}
		drawContent(this.width, this.height);
	}
	
	@Override
	public void onMouseRelease() {
		if(last == null) {
			for(InputPanel panel : panels) {
				panel.onMouseRelease();
			}
		}else {
			last.mouseReleased();
			last = null;
		}
		
		drawContent(getW(), getH());
	}
	
	@Override
	public void onKeyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if(editing != null) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				editing.removeLetter();
				break;
			case KeyEvent.VK_LEFT:
				editing.moveCursor(-1);
				break;
			case KeyEvent.VK_RIGHT:
				editing.moveCursor(1);
				break;
			default:
				editing.addLetter(c);
				break;
			}
		}
		drawContent(getW(), getH());
	}
}
