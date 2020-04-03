package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	
	private Option options[];
	
	public StartScreen(int w,int h,MainScreen ms) {
		this.objects = new ArrayList<GUIObject>();
		this.width = w;
		this.height = h;
		this.ms = ms;
		
		this.options = new Option[3];
		options[0] =  new NewProjectOption(ms);
		options[1] =  new NewProjectOption(ms);
		options[2] =  new ContinueRecentOption(ms);
		
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
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	private void addButtons() {
		Button btn;
		InputField input;
		for(int i=0;i<3;i++) {
			btn = new Button(100, 100+i*30, 50, 19, options[i]);
			objects.add(btn);
		}
		input = new InputField("Test",100, 260, 150, 50,1);
		objects.add(input);
	}
	
	@Override
	public void onClick(int x,int y) {
		for(GUIObject object:objects) {
			if(x>=object.getX() && x<=object.getX()+object.getWidth() && y>=object.getY() && y<=object.getY()+object.getHeight()) {
				object.onClick();
				break;
			}
		}
		drawContent(this.width, this.height);
	}
}
