package com.nm.wpc.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.nm.wpc.input.InputListener;
import com.nm.wpc.window.Window;

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
		super(w, h);
		this.start = new StartScreen(w, h,this);
		this.working = new WorkingScreen(w, h,this);
		
		this.setListener(new InputListener(this));
		this.addMouseListener(listener);
		
		this.panelsActivity = false;
		
		work=false;
		this.content = start.getContent();
	}
	
	public MainScreen(int w,int h,Window window) {
		super(w, h, window);
		this.start = new StartScreen(w, h,this);
		this.working = new WorkingScreen(w, h,this);
		this.setListener(new InputListener(this));
		
		this.addMouseListener(listener);
		
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
	
	public Dimension createPanelDimension(InputPanel ip) {
		return new Dimension(200, ip.getObjects().size()*80+50);
	}

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener il) {
		this.listener = il;
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(work)
			working.onMousePressed(x, y);
		else
			start.onMousePressed(x, y);
		
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
}
