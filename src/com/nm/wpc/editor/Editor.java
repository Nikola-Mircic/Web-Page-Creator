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
 * Class: com.nm.wpc.editor.Editor
 * Superclass :
 * Used for: allows you to change different pages or elements
 */
package com.nm.wpc.editor;

import com.nm.wpc.screen.*;

public class Editor extends Screen{
	private static final long serialVersionUID = 1L;
	
	protected WorkingScreen ws;
	
	public Editor(int x,int y,int width,int height,WorkingScreen ws) {
		super(x,y,width,height);
		this.setWs(ws);
	}
	
	public void update(int x,int y,int width,int height,WorkingScreen ws) {
		setX(x);
		setY(y);
		setW(width);
		setH(height);
		setWs(ws);
		drawContent(width, height);
	}

	public WorkingScreen getWs() {
		return ws;
	}

	public void setWs(WorkingScreen ws) {
		this.ws = ws;
	}
}
