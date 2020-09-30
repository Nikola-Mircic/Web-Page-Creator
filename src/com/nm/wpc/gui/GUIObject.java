/*  Copyright 2020 Nikola Mircic
  
    This file is part of Web Page Creator.

    Web Page Creator is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Web Page Creator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Web Page Creator.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Class: com.nm.wpc.gui.GUIObject
 * Superclass :  
 * Used for: defining gui object that can interact with the user(i.e. buttons)
 */

package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.screen.Screen;


public class GUIObject {
	protected int x, y, width, height;
	protected BufferedImage img;
	protected Screen container;
	
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
	
	public void mousePressed() {}
	
	public void mousePressed(int x,int y) {}
	
	public void mouseReleased() {}
	
	public BufferedImage getImg() {
		return this.img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public Screen getContainer() {
		return container;
	}

	public GUIObject setContainer(Screen container) {
		this.container = container;
		return this;
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
