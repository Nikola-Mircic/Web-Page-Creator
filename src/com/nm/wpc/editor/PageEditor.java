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
 * Class: com.nm.wpc.editor.PageEditor
 * Superclass : com.nm.editor.Editor
 * Used for: displays settings of your project and allows you to change them
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.Page;
import com.nm.elems.attribute.Attribute;
import com.nm.wpc.screen.WorkingScreen;

public class PageEditor extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;
	private Page page;
	
	public PageEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}
	
	public PageEditor(int x, int y, int width, int height, WorkingScreen ws,Page p) {
		super(x, y, width, height, ws);
		
		this.setPage(p);
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		bckg = new Color(78, 146, 74);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
