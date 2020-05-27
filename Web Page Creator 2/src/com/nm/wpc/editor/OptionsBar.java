package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.screen.WorkingScreen;

public class OptionsBar extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;

	public OptionsBar(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		bckg = Color.WHITE;
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		bckg = new Color(150, 150, 150);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = Color.WHITE;
		drawContent(width, height);
	}

}
