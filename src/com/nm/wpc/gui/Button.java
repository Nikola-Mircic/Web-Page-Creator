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
 * Class: com.nm.wpc.gui.Button
 * Superclass :  com.nm.wpc.gui.GUIObject
 * Used for: Creating buttons with given options
 */

package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.editor.option.Option;

public class Button extends GUIObject{
	private Option option;
	private Color bckg;
	private GUIObject guiObject;
	
	public Button(int x,int y,int width,int height,Option option) {
		super(x,y,width,height);
		this.option = option;
		bckg = Color.WHITE;
		createImage();
	}
	
	public Button(String title,int x,int y,int width,int height,Option option) {
		super(x,y,width,height);
		this.option = option;
		this.option.setOptName(title);
		bckg = Color.WHITE;
		createImage();
	}
	
	private void createImage() {
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.img.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		this.drawBorder(g,0,0,width,height);
		int fontSize = Math.min(findFontSize(),25);
		g.setFont(new Font("",Font.PLAIN,fontSize));
		g.drawString(this.option.getOptName(), (width-this.option.getOptName().length()*10)/4+5, height/2+fontSize/2);
	}
	
	public int findFontSize() {
		int min=1,max=this.height,curr=0;
		while (min<max) {
			curr=min+(max-min)/2;
			int width = img.getGraphics().getFontMetrics(new Font("Serif",Font.PLAIN,curr)).stringWidth(option.getOptName());
			if(width>=this.width) {
				if(curr<=height/2) {
					String newTitle = option.getOptName();
					int n = newTitle.length()/2;
					option.setOptName(newTitle.substring(0,n)+System.lineSeparator()+newTitle.substring(n));
					return curr;
				}
				max = curr-1;
			}else if(width<this.width) {
				min = curr+1;
			}
		}
		return (curr==0)?20:curr-5;
	}
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
	
	public GUIObject getGuiObject() {
		return guiObject;
	}

	public Button setGuiObject(GUIObject guiObject) {
		this.guiObject = guiObject;
		return this;
	}

	@Override
	public void mousePressed(int x,int y) {
		this.bckg = new Color(230,230,230);
		this.createImage();
		option.make(this);
	}

	@Override
	public void mouseReleased() {
		this.bckg = Color.WHITE;
		this.createImage();
	}
}
