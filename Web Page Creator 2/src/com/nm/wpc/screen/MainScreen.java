package com.nm.wpc.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.input.InputListener;

/*
 * Classname: Screen
 * Used for: Organizing content of app
 */

public class MainScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private StartScreen start;
	private WorkingScreen working;
	
	private InputListener listener;
	
	private boolean work = false;
	
	public MainScreen(int w,int h) {
		
		this.start = new StartScreen(w, h,this);
		this.working = new WorkingScreen(w, h,this);
		
		this.setListener(new InputListener(this));
		this.addMouseListener(listener);
		
		this.panels = new ArrayList<InputPanel>();
		this.panelsActivity = false;
		
		work=false;
		this.content = start.getContent();
	}
	
	@Override
	public void paint(Graphics g) {
		if(work) {
			this.content = working.getContent();
		}else {
			this.content = start.getContent();
		}
		g.drawImage(this.content, 0, 0, null);
		if(panelsActivity)
			drawPanels(g);
		else if(panelsActivity==false){
			panels.removeAll(panels);
		}
		g.dispose();
	}
	
	private void drawPanels(Graphics g) {
		for (InputPanel panel : panels) {
			g.drawImage(panel.getContent(), panel.getSource().getX()+panel.getSource().getWidth(), panel.getSource().getY(), null);
		}
	}
	
	public void changeContent() {
		work = !work;
		if(work) {
			this.content = working.getContent();
		}else {
			this.content = start.getContent();
		}
		repaint();
	}
	
	public void updateSize(int width,int height) {
		start.drawContent(width, height);
		working.drawContent(width, height);
		repaint();
	}
	
	public Dimension createPanelDimension() {
		return new Dimension(200, 300);
	}

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener il) {
		this.listener = il;
	}
	
	@Override
	public void onClick(int x,int y) {
		panelsActivity = false;
		InputPanel temp = null;
		for (InputPanel panel : panels) {
			int x1 = panel.getSource().getX()+panel.getSource().getWidth();
			int y1 =  panel.getSource().getY();
			int x2 = x1+panel.getContent().getWidth();
			int y2 = y1+panel.getContent().getHeight();
			if(x1<x && x<x2 && y1<y && y<y2) {
				panelsActivity = true;
				temp = panel;
			}
		}
		
		if(panelsActivity)
			sendToBack(temp);
		
		if(work)
			working.onClick(x, y);
		else
			start.onClick(x, y);
		repaint();
	}
	
	private void sendToBack(InputPanel panel){
		InputPanel temp = panel;
		panels.remove(panel);
		panels.add(temp);
	}
}
