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
 * Class: com.nm.wpc.editor.ElementSelector
 * Superclass : com.nm.editor.Editor
 * Used for: Creating a list of elements that the user can add to the page
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.nm.wpc.screen.*;
import com.nm.wpc.gui.*;
import com.nm.elems.tagsystem.*;
import com.nm.wpc.gui.option.*;

public class ElementSelector extends Editor{
	private static final long serialVersionUID = 1L;

	private Color bckg;
	
	public ElementSelector(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		bckg = Color.WHITE;
		drawContent(width, height);
	}
	
	@Override
	public void drawContent(int width,int height) {
		generateButtons();
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		this.drawObjects(g);
	}
	
	private	void generateButtons() {
		controler.setObjects(new ArrayList<GUIObject>());
		Button btn;
		Tag[] tags = Tag.values();
		int n = tags.length;
		
		int w = Math.min((this.width/(tags.length-6)),300),h = this.height;
		int i;
		
		for(i=0;i<n-1;++i) {
			btn = null;
			if(tags[i].name().indexOf("_")!=-1) {
				if(tags[i].name().substring(0,7).equals("HEADING")){
					btn = new Button("HEADING", this.x+i*w, this.y, w, h, new Option(this.ws.getMs()){
						@Override
						public void make(GUIObject source) {
							InputPanel ip = new InputPanel(source,source.getX(),source.getY()+source.getHeight()/2,w*9/10,h*6);
							Button tempBtn;
							int s = findHeading(tags);
							for(int j=0;j<6;++j) {
								int s2 = j;
								tempBtn = new Button(tags[s+j].name(), ip.getX(), ip.getY()+j*h, w*12/10, h, new Option(){
									@Override
									public void make(GUIObject source) {
										ws.pickElement(tags[s+s2]);
									}
								});
								ip.addGUIObject(tempBtn);
							}
							ip.drawContent();
							ms.drawPanel(ip);
						}
					});
					i+=5;
				}
			}
			if(btn==null){
				int s = i;
				int pos = (i<findHeading(tags))?i:i-5;
				btn = new Button(tags[s].name(), this.x+pos*w, this.y, w, h, new Option(){
					@Override
					public void make(GUIObject source) {
						ws.pickElement(tags[s]);
					}
				});
			}
			
			controler.addButton(btn);
		}
	}
	
	private int findHeading(Tag[] temp) {
		for(int i=0;i<temp.length;++i) {
			if(temp[i].name().equals("HEADING_1")) 
				return i;
		}
		return -1;
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		controler.activateOnClick(x, y);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		controler.releaseObjects();
		drawContent(width, height);
	}
}
