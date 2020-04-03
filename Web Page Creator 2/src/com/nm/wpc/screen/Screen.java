package com.nm.wpc.screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import com.nm.wpc.gui.GUIObject;

abstract class Screen extends JPanel{
	private static final long serialVersionUID = 1L;
	
	protected int width,height;
	
	protected BufferedImage content;
	
	protected boolean panelsActivity;
	
	protected List<GUIObject> objects;
	protected List<InputPanel> panels;
	
	protected void drawContent(int width,int height) {}
	
	protected void onClick(int x,int y) {}
	
	public void drawPanel(InputPanel ip) {
		this.panels.add(ip);
		panelsActivity = true;
	}
	
	public BufferedImage getContent() {
		return content;
	}
}
