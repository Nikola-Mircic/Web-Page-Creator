package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GUIObject {
	protected int x, y, width, height;
	protected BufferedImage img;
	
	public GUIObject(int x,int y,int width,int height) {
		setX(x);
		setY(y);
		setWidth(width);;
		setHeight(height);
	}
	
	protected void drawBorder(Graphics g,int x,int y,int width,int height,int border) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, border);
		g.fillRect(x+width-border, y, border, height);
		g.fillRect(x, y+height-border, width, border);
		g.fillRect(x, y, border, height);
	}
	
	protected void drawBorder(Graphics g,int x,int y,int width,int height) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width-1, height-1);
	}
	
	public void onClick() {}
	
	public BufferedImage getImg() {
		return this.img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
