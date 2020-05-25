/*
 * Class: com.nm.wpc.screen.WorkinScreen
 * Superclass : 
 * Used for: Editing content in a project
 */

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
	public void drawContent(int w,int h) {
		this.width = w;
		this.height = h;
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(x>(width-66) && y>(height-64))
			ms.changeContent();
	}
}