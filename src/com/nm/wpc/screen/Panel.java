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
import java.awt.Dimension;
import java.awt.Graphics;

import com.nm.wpc.editor.option.Option;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.input.InputListener;

public class Panel extends Screen{
	private static final long serialVersionUID = 1L;
	private GUIObject last;
	private InputListener listener;
	
	public Panel() {
		super();
		
		listener = new InputListener(this);
		this.addMouseListener(listener);
	}
	
	public Panel(int w,int h) {
		super(w, h);
		
		listener = new InputListener(this);
		this.addMouseListener(listener);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		this.drawObjects(g);
	}
	
	@Override
	public void onMousePressed(int x, int y) {
		last = findEditingField();
		
		if(last != null) {
			((InputField)last).setEditing(false);
			last=null;
		}
		
		GUIObject object = checkOnClick(x, y);
		if(object != null) {
			object.mousePressed(x, y);
		}
		
		repaint();
	}
	
	@Override
	public void onMouseRelease() {
		if(last != null) {
			last.mouseReleased();
			last = null;
		}
		repaint();
	}

	public void addInputField(String label,int x,int y,int width,int height,int type) {
		InputField input = new InputField(label,x,y,width,height,type);
		addGUIObject(input);
	}
	
	public void addButton(String title,int x,int y,int width,int height,Option option) {
		Button btn = new Button(title, x, y, width, height, option);
		addGUIObject(btn);
	}
	
	public void addButton(int x,int y,int width,int height,Option option) {
		Button btn = new Button(x, y, width, height, option);
		addGUIObject(btn);
	}
	
	public Dimension getDimension() {
		for(GUIObject object : controler.getObjects()) {
			if(object.getWidth() > width)
				this.width = object.getWidth()+40;
		}
		
		this.height = controler.getObjects().get(controler.getObjects().size()-1).getY()+controler.getObjects().get(controler.getObjects().size()-1).getHeight()+40;
		
		return new Dimension(this.width,this.height);
	}

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener listener) {
		this.listener = listener;
	}
	
}
