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

package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;

public class InputPanel extends Screen{
	private static final long serialVersionUID = 1L;
	private String TITLE;
	private GUIObject source;
	private GUIObject last;
	
	public InputPanel(GUIObject source, int x, int y) {
		super(0, 0);
		this.x = x;
		this.y = y;
		this.setSource(source);
		
		this.TITLE = "";
		drawContent();
	}
	
	public InputPanel(GUIObject source, int x, int y, int width, int height) {
		super(width, height);
		this.x = x;
		this.y = y;
		this.setSource(source);
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.TITLE = "";
		
		this.drawContent(width, height);
	}
	
	public void drawContent() {
		getDimensions();
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		if(TITLE != "") {
			g.setFont(new Font("",Font.BOLD,15));
			g.drawString(TITLE, 2, 17);
		}
		drawObjects(g);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		if(TITLE != "") {
			g.setFont(new Font("",Font.BOLD,15));
			g.drawString(TITLE, 2, 17);
		}
		drawObjects(g);
	}
	
	public void drawChildPanels(Graphics g) {
		for(InputPanel panel : panels) {
			g.drawImage(panel.getContent(), panel.getX(), panel.getY(), null);
			panel.drawChildPanels(g);
		}
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
	
	@Override
	public void onMousePressed(int x,int y) {
		this.panelsActivity = false;
		InputPanel temp = null;
		
		temp = getClicked(x, y);
		
		if(temp != null) {
			temp.onMousePressed(x, y);
			this.drawContent(this.width, this.height);
			return;
		}else {
			panels.removeAll(panels);
		}
		
		GUIObject object = checkOnClick(x, y);
		if(object != null) {
			if(object instanceof Button) {
				last = object;
			}
			object.mousePressed(x, y);
		}
		
		drawContent(this.width,this.height);
	}
	
	@Override
	public void onMouseRelease() {
		if(this.last == null) {
			for(InputPanel panel : panels) {
				panel.onMouseRelease();
			}
		}else {
			last.mouseReleased();
			last = null;
		}
		
		
		drawContent(this.width,this.height);
	}
	
	private void getDimensions() {
		this.height = 50;
		this.width = 180;
		if(!controler.getObjects().isEmpty()) {
			if(this.TITLE=="")
				this.height = controler.getObjects().get(controler.getObjects().size()-1).getY()-this.y+controler.getObjects().get(controler.getObjects().size()-1).getHeight();
			else
				this.height += controler.getObjects().get(controler.getObjects().size()-1).getY()-this.y+controler.getObjects().get(controler.getObjects().size()-1).getHeight();
			this.width = controler.getObjects().get(0).getWidth();
			for(GUIObject object:controler.getObjects()) {
				if(object.getWidth()>this.width){
					this.width = object.getWidth();
				}
			}
		}
	}
	
	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String title) {
		this.TITLE = title;
		drawContent(width, height);
	}

	public GUIObject getSource() {
		return source;
	}

	public void setSource(GUIObject source) {
		this.source = source;
	}

	public GUIObject getLast() {
		return last;
	}

	public void setLast(GUIObject last) {
		this.last = last;
	}
}
