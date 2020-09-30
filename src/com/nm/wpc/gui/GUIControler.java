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
 * Class: com.nm.wpc.gui.GUIControler
 * Superclass :  
 * Used for: Organizing subclasses of GUIObject
 */

package com.nm.wpc.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import com.nm.wpc.screen.InputPanel;

public class GUIControler {
	private List<GUIObject> objects;
	
	public GUIControler() {
		this.objects = new ArrayList<GUIObject>();
	}
	
	public void addObject(GUIObject object) {
		this.objects.add(object);
	}
	
	public void addButton(Button btn) {
		this.addObject(btn);
	}
	
	public void addInputField(InputField inf) {
		this.addObject(inf);
	}
	
	public InputField findEditingField(List<InputPanel> panels) {
		InputField inf = null;
		for(InputPanel panel : panels) {
			inf = panel.findEditingField();
			if(inf != null)
				return inf;
		}
		
		for(GUIObject field : objects) {
			if(field instanceof InputField) {
				if(((InputField) field).isEditing())
					return (InputField)field;
			}
		}
		
		return inf;
	}
	
	public GUIObject checkOnClick(int x,int y) {
		GUIObject clicked = null;
		for(GUIObject object:objects) {
			if(x>=object.getX() && x<=object.getX()+object.getWidth() && y>=object.getY() && y<=object.getY()+object.getHeight()) {
				clicked = object;
			}
		}
		return clicked;
	}
	
	public void activateOnClick(int x,int y) {
		GUIObject obj = null;
		for(GUIObject object:objects) {
			if(x>=object.getX() && x<=object.getX()+object.getWidth() && y>=object.getY() && y<=object.getY()+object.getHeight()) {
				obj = object;
			}else {
				if(object instanceof InputField) {
					if(((InputField)object).isEditing())
						((InputField)object).setEditing(false);
				}
			}
		}
		if(obj != null)
			obj.mousePressed(x, y);
	}
	
	public void releaseObjects() {
		for(GUIObject object:objects) {
			object.mouseReleased();
		}
	}
	
	public void drawObjects(Graphics g, int x, int y) {
		if(this.objects == null)
			return;
		int n = this.objects.size();
		for(int i=0;i<n;i++) {
			g.drawImage(objects.get(i).getImg(), objects.get(i).getX()-x, objects.get(i).getY()-y, null);
		}
	}

	public List<GUIObject> getObjects() {
		return objects;
	}

	public void setObjects(List<GUIObject> objects) {
		this.objects = objects;
	}
}
