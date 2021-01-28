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
import com.nm.wpc.gui.option.Option;
import com.nm.wpc.editor.changes.Changes;
import com.nm.wpc.editor.changes.PageAttributeChange;
import com.nm.wpc.gui.*;
import com.nm.wpc.screen.InputPanel;
import com.nm.wpc.screen.WorkingScreen;

public class PageEditor extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;
	private Page page;
	
	private List<List<GUIObject>> toShow;
	
	public PageEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		this.setToShow(new ArrayList<List<GUIObject>>());
		
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}
	
	public PageEditor(int x, int y, int width, int height, WorkingScreen ws,Page p) {
		super(x, y, width, height, ws);
		
		this.setToShow(new ArrayList<List<GUIObject>>());
		
		this.setPage(p);
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}
	
	@Override
	public void update(int x, int y, int width, int height, WorkingScreen ws) {
		super.update(x, y, width, height, ws);
		this.toShow.clear();
	}
	
	@Override
	public void drawContent(int width,int height) {
		if(toShow.isEmpty() && page != null)
			generateObjects();
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		drawObjects(g);
	}
	
	private void generateObjects() {
		if(page==null)
			return;
		
		if(!toShow.isEmpty() && page.getAttributes().isEmpty())
			return;
		
		System.out.println("Generating new editor!!");
		
		controler.setObjects(new ArrayList<GUIObject>());
		toShow = new ArrayList<List<GUIObject>>();
		int fWidth=this.width,fHeight=80;
		
		toShow.add(new ArrayList<GUIObject>());
		
		String lastName = findGroup(page.getAttributes().get(0).getName());
		toShow.get(0).add((new InputField(page.getAttributes().get(0).getName(),this.x,this.y,fWidth,fHeight,1)).setValue(page.getAttributes().get(0).getValue()));
		int idx = 0;
		for(int i=1;i<page.getAttributes().size();++i) {
			if(findGroup(page.getAttributes().get(i).getName()).equals(lastName)) {
				if(toShow.get(idx).size() == 1)
					toShow.get(idx).get(0).setWidth(fWidth*8/10);
				toShow.get(idx).add((new InputField(page.getAttributes().get(i).getName(),this.x,this.y+toShow.get(idx).size()*fHeight,fWidth*8/10,fHeight,1)).setValue(page.getAttributes().get(i).getValue()));
			}else {
				toShow.add(new ArrayList<GUIObject>());
				idx++;
				toShow.get(idx).add((new InputField(page.getAttributes().get(i).getName(),this.x,this.y+idx*fHeight,fWidth,fHeight,1)).setValue(page.getAttributes().get(i).getValue()));
				lastName = findGroup(page.getAttributes().get(i).getName());
			}
		}
		
		for(int i=0;i<toShow.size();++i) {
			if(toShow.get(i).isEmpty())
				break;
			if(toShow.get(i).size()>1) {
				int pos = i;
				int h = fHeight;
				controler.addButton(new Button(findGroup(((InputField)toShow.get(pos).get(0)).getLabel()), this.x, this.y+pos*fHeight, fWidth, fHeight, new Option(ws.getMs()) {
					@Override
					public void make(GUIObject source) {
						int ipw = source.getWidth()*8/10;
						int iph = toShow.get(pos).size()*h;
						int ipx = source.getX()-ipw*8/10;
						int ipy = source.getY();
						if(ipy+iph>height) {
							ipy = height-iph;
						}
						
						InputPanel ip = new InputPanel(source,ipx,ipy,ipw,iph);
						for(int j=0;j<toShow.get(pos).size();++j) {
							toShow.get(pos).get(j).setWidth(ipw);
							toShow.get(pos).get(j).setX(ipx);
							toShow.get(pos).get(j).setY(ipy+j*h);
							ip.addGUIObject(toShow.get(pos).get(j));
						}
						
						ms.drawPanel(ip);
					}
				}));
			}else {
				controler.addObject(toShow.get(i).get(0));
			}
		}
	}
	
	//Group contain attributes with the same beginning 
	private String findGroup(String attrName) {
		int idx = attrName.indexOf('-');
		if(idx > 0) {
			return attrName.substring(0, idx);
		}
		return attrName;
	}
	
	public void checkValues() {
		if(page==null)
			return;
		int idx = 0;
		for(List<GUIObject> list : toShow) {
			for(GUIObject field : list) {
				if(!((InputField)field).getText().equals(page.getAttributeValue(idx))) {
					String lastData = page.getAttributeValue(idx);
					page.setAttributeValue(idx,((InputField)field));
					String data = ((InputField)field).getText();
					Changes.addChange(PageAttributeChange.makeChange(this.ws, page, idx, data, lastData));
					((InputField)field).setEditing(false);
				}
				idx++;
			}
		}
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(x<this.x || y<this.y) {
			controler.releaseObjects();
		}
		controler.activateOnClick(x, y+yOffset);

		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		controler.releaseObjects();
		drawContent(width, height);
	}
	
	@Override
	public void onMouseWheel(int x,int y,int d) {
		this.yOffset +=d;
		if(this.yOffset<0)
			this.yOffset = 0;
		drawContent(width, height);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public void setPageAttributes(Page element) {
		this.setPage(element);
		generateObjects();
		drawContent(width, height);
	}

	public List<List<GUIObject>> getToShow() {
		return toShow;
	}

	public void setToShow(List<List<GUIObject>> toShow) {
		this.toShow = toShow;
	}
}
