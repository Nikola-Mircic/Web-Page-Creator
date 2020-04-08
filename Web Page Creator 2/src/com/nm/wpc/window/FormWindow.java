package com.nm.wpc.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.nm.wpc.screen.Screen;
import com.nm.wpc.editor.option.SelectFileOption;
import com.nm.wpc.gui.*;
import com.nm.wpc.input.InputListener;

public class FormWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	private String title;
	private Panel p;

	public FormWindow(String title) {
		this.title = title;
	}
	
	public void createWindow() {
		this.setTitle(title);
		this.setSize(new Dimension(300,500));
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		
		p = new Panel(300,500);
		this.add(p);
		
		this.setVisible(true);
	}
}

class Panel extends Screen{
	private static final long serialVersionUID = 1L;
	private GUIObject last;
	
	public Panel(int w,int h) {
		super(w, h);
		this.objects = new ArrayList<GUIObject>();
		InputField input;
		input = new InputField("Project name:", 20, 20, 260,80,1);
		this.objects.add(input);
		input = new InputField("Project location:", 20, 110, 260,80,1);
		this.objects.add(input);
		Button btn = (new Button(225, 115, 30, 30, new SelectFileOption(JFileChooser.DIRECTORIES_ONLY))).setGuiObject(input);
		this.objects.add(btn);
		input = new InputField("Entry point:", 20, 200, 260,80,1);
		this.objects.add(input);
		
		InputListener il = new InputListener(this);
		this.addMouseListener(il);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		this.drawObjects(g);
	}
	
	@Override
	public void onClick(int x, int y) {
		for(GUIObject object:objects) {
			if(x>=object.getX() && x<=object.getX()+object.getWidth() && y>=object.getY() && y<=object.getY()+object.getHeight()) {
				last = object;
			}
		}
		last.mousePressed();
		repaint();
	}
	
	@Override
	public void onRelease() {
		if(last != null) {
			last.mouseReleased();
			last = null;
		}
		repaint();
	}
	
}
