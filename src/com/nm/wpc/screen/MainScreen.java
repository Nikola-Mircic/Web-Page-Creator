/*
 * Class: com.nm.wpc.screen.MainScreen
 * Superclass : com.nm.wpc.screen.Screen 
 * Used for: Organizing content of app
 */

package com.nm.wpc.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.nm.wpc.input.InputListener;
import com.nm.wpc.window.Window;

public class MainScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private StartScreen start;
	private WorkingScreen working;
	
	private InputListener listener;
	
	private boolean work = true;
	
	public MainScreen(int w,int h) {
		super(w, h);
		this.start = new StartScreen(w, h,this);
		this.working = new WorkingScreen(w, h,this);
		
		this.setListener(new InputListener(this));
		this.addMouseListener(listener);
		
		this.panelsActivity = false;
		
		work=true;
		//this.content = start.getContent();
	}
	
	public MainScreen(int w,int h,Window window) {
		super(w, h, window);
		this.start = new StartScreen(w, h,this);
		this.working = new WorkingScreen(w, h,this);
		this.setListener(new InputListener(this));
		
		this.addMouseListener(listener);
		
		this.panelsActivity = false;
		
		work=true;
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
		else{
			panels.clear();
		}
		g.dispose();
	}
	
	private void drawPanels(Graphics g) {
		for (InputPanel panel : panels) {
			g.drawImage(panel.getContent(), panel.getSource().getX()+panel.getSource().getWidth(), panel.getSource().getY(), null);
		}
	}
	
	public void changeContent() {
		panelsActivity = false;
		work = !work;
		repaint();
	}
	
	public void updateSize(int width,int height) {
		start.drawContent(width, height);
		working.drawContent(width, height);
		repaint();
	}
	
	public Dimension createPanelDimension(InputPanel ip) {
		return new Dimension(200, ip.getObjects().size()*80+50);
	}

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener il) {
		this.listener = il;
	}
	
	public Screen getActiveScreen() {
		if(work)
			return working;
		return start;
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		InputPanel panel = null;
		if(panelsActivity) {
			panel = getClicked(x, y);
			if(panel==null) {
				panelsActivity = false;
				panels.clear();
			}else {
				panel.onMousePressed(x, y);
			}
		}else {
			if(work)
				working.onMousePressed(x, y);
			else
				start.onMousePressed(x, y);
		}
		repaint();
	}
	
	@Override
	public void onMouseDragged(int x,int y) {
		if(work)
			working.onMousePressed(x, y);
		else
			start.onMousePressed(x, y);
		
		repaint();
	}
	
	@Override
	public void onMouseRelease() {
		if(panelsActivity) {
			for(InputPanel panel:panels) {
				panel.onMouseRelease();
			}
		}
		if(work)
			working.onMouseRelease();
		else
			start.onMouseRelease();
		
		repaint();
	}
	
	@Override
	public void onKeyPressed(KeyEvent e) {
		if(work)
			working.onKeyPressed(e);
		else
			start.onKeyPressed(e);
		
		repaint();
	}
	
	public InputPanel getClicked(int x,int y) {
		for(InputPanel panel : panels) {
			int x1 = panel.getX();
			int y1 =  panel.getY();
			int x2 = x1+panel.getW();
			int y2 = y1+panel.getH();
			if(x1<x && x<x2 && y1<y && y<y2) {
				return panel;
			}else {
				return panel.getClicked(x, y);
			}
		}
		return null;
	}
	
}
