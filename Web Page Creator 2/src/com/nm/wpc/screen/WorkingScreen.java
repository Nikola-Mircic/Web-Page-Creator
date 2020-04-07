package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class WorkingScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private MainScreen ms;
	
	public WorkingScreen(int w,int h,MainScreen ms) {
		super(w, h);
		this.ms = ms;
		this.drawContent(w,h);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	@Override
	public void onClick(int x,int y) {
		ms.changeContent();
	}
}