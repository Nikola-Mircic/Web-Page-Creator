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
	
	public void drawObjects(Graphics g, int x, int y) {
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
