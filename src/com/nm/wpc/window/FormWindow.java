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

package com.nm.wpc.window;

import javax.swing.JFrame;

import com.nm.wpc.screen.*;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.gui.*;

public class FormWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private String title;
	int width,height;
	
	private Panel p;

	public FormWindow(String title,int w,int h) {
		this.title = title;
		this.width = w;
		this.height = h;
		
		p = new Panel();
	}
	
	public void createWindow() {
		this.setTitle(title);
		this.setSize(p.getDimension());
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		
		this.add(p);
		this.addKeyListener(p.getListener());
		
		this.setVisible(true);
	}
	
	public void addInputField(InputField newIf) {
		p.getObjects().add(newIf);
	}
	
	public void addInputField(String label,int x,int y,int width,int height,int type) {
		InputField input = new InputField(label,x,y,width,height,type);
		p.getObjects().add(input);
	}
	
	public void addButton(Button btn) {
		p.getObjects().add(btn);
	}
	
	public void addButton(String title,int x,int y,int width,int height,Option option) {
		Button btn = new Button(title, x, y, width, height, option);
		p.getObjects().add(btn);
	}
	
	public void addButton(int x,int y,int width,int height,Option option) {
		Button btn = new Button(x, y, width, height, option);
		p.getObjects().add(btn);
	}
	
	public void addGUIObject(GUIObject object) {
		p.getObjects().add(object);
	}
	
	public Panel getPanel() {
		return this.p;
	}
}
