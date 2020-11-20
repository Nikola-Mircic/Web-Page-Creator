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

package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.nm.wpc.editor.option.SelectColorOption;

public class ColorField extends InputField {
	private Color color;
	Button colorBtn;
	
	public ColorField(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public ColorField(String label, int x, int y, int width, int height, int type) {
		super(label, x, y, width, height, type);
	}
	
	@Override
	protected void drawImage() {
		super.drawImage();
		if(width<=0 || height<=0)
			return;
		colorBtn = new Button("Select", this.width-85, 5, 65, 20, new SelectColorOption()).setGuiObject(this);
		Graphics g = this.img.getGraphics();
		this.color = getColor(this.getText());
		g.setColor(this.color);
		g.fillRect(this.width-110, 5, 20, 20);
		g.drawImage(colorBtn.getImg(), colorBtn.getX(), colorBtn.getY(), null);
	}
	
	private Color getColor(String raw) {
		if(raw.length()==0)
			return new Color(0, 0, 0, 0);
		else {
			String color[] = raw.substring(raw.indexOf('(')+1,raw.indexOf(')')).split(",");
			int r,g,b,a;
			for(int i=0;i<4;++i) {
				if(color[i].equals(""))
					color[i] = "0";
				else if(Float.parseFloat(color[i])<0)
					color[i]="0";
				else {
					if(Float.parseFloat(color[i])>255 && i<3)
						color[i]="255";
					else if(Float.parseFloat(color[i])>1.0 && i==3)
						color[i]="1.0";
				}
			}
			r = Integer.parseInt(color[0]);
			g = Integer.parseInt(color[1]);
			b = Integer.parseInt(color[2]);
			a = (int)(Float.parseFloat(color[3])*255);
			
			return new Color(r, g, b, a);
		}
	}
	
	@Override
	public void mousePressed(int x,int y) {
		super.mousePressed(x, y);
		x-=this.x;
		y-=this.y;
		if(colorBtn.getX()<x && (colorBtn.getX()+colorBtn.getWidth())>x && colorBtn.getY()<y && (colorBtn.getY()+colorBtn.getHeight())>y) {
			colorBtn.mousePressed(x, y);
		}
		drawImage();
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		this.color = getColor(text);
	}
	
	public InputField setValue(String newValue) {
		super.setText(newValue);
		this.color = getColor(newValue);
		drawImage();
		return this;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(color==null)
			return;
		this.color = color;
		double a = Math.round(color.getAlpha()*10/255.0)/10;
		String textColor = "rgba("+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+a+")";
		this.setText(textColor);
	}

}
