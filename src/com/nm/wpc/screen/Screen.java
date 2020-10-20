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

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.nm.wpc.gui.GUIControler;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.window.Window;


public abstract class Screen extends JPanel{
	private static final long serialVersionUID = 1L;
	
	protected Window window;
	
	protected int x,y;
	protected int yOffset;
	protected int width,height;
	
	protected BufferedImage content;
	
	protected boolean panelsActivity;
	
	protected GUIControler controler;
	protected List<InputPanel> panels;
	
	public Screen() {
		setyOffset(0);
		
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
	
	public Screen(int width,int height) {
		setW(width);
		setH(height);
		setyOffset(0);
		
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}

	public Screen(int width,int height,Window window) {
		setW(width);
		setH(height);
		setyOffset(0);
		setWindow(window);
		
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
	
	public Screen(int x,int y,int width,int height) {
		setX(x);
		setY(y);
		setyOffset(0);
		setW(width);
		setH(height);
		setWindow(window);
		
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
	
	public void drawContent() {
		this.drawContent(width, height);
	}
	
	protected void drawContent(int width,int height) {}
	
	public void onMousePressed(int x,int y) {}
	
	public void onMouseDragged(int x,int y) {}
	
	public void onMouseWheel(int x,int y,int d) {}
	
	public void onMouseRelease() {}
	
	public void onKeyPressed(KeyEvent e) {}
	
	public void drawPanel(InputPanel ip) {
		this.panels.add(ip);
		this.panelsActivity = true;
	}
	
	protected void drawObjects(Graphics g) {
		controler.drawObjects(g,this.x,this.y);
	}
	
	public InputField findEditingField() {
		for(GUIObject obj:controler.getObjects()) {
			if(obj instanceof InputField) {
				if(((InputField) obj).isEditing())
					return ((InputField)obj);
			}
		}
		return controler.findEditingField(panels);
	}
	
	protected GUIObject checkOnClick(int x,int y) {
		return controler.checkOnClick(x,y);
	}
	
	protected void sendToBack(InputPanel panel){
		InputPanel temp = panel;
		panels.remove(panel);
		panels.add(temp);
	}
	
	public void addGUIObject(GUIObject object) {
		this.controler.addObject(object);
	}
	
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public BufferedImage getContent() {
		this.drawContent(width, height);
		return content;
	}

	public List<GUIObject> getObjects() {
		return controler.getObjects();
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

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getW() {
		return width;
	}

	public void setW(int width) {
		this.width = width;
	}

	public int getH() {
		return height;
	}

	public void setH(int height) {
		this.height = height;
	}

	public GUIControler getControler() {
		return controler;
	}

	public void setControler(GUIControler controler) {
		this.controler = controler;
	}
}
