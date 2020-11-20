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

/*Class: com.nm.wpc.editor.ElementEditor
 * Superclass : com.nm.editor.Editor
 * Used for: allows you to change attributes of single element and displays it
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.PageElement;
import com.nm.elems.attribute.Attribute;
import com.nm.wpc.gui.*;
import com.nm.wpc.screen.*;
import com.nm.wpc.editor.option.*;

public class ElementEditor extends Editor{
	private static final long serialVersionUID = 1L;
	
	private PageElement edited;
	private List<List<GUIObject>> toShow;
	private Color bckg;
	
	private enum InputFieldType{
		InputField,
		ColorField,
		NumberField
	};
	
	public ElementEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		this.setToShow(new ArrayList<List<GUIObject>>());
		
		bckg = new Color(185, 186, 189);
		drawContent(width, height);
	}
	
	@Override
	public void drawContent(int width,int height) {
		if(toShow.isEmpty() && edited != null)
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
		if(edited==null)
			return;
		if(!toShow.isEmpty() && edited.getAttributes().isEmpty())
			return;
		
		controler.setObjects(new ArrayList<GUIObject>());
		toShow = new ArrayList<List<GUIObject>>();
		int fWidth=this.width-10,fHeight=80;
		
		toShow.add(new ArrayList<GUIObject>());
		
		String lastName = findGroup(edited.getAttributes().get(0).getName());
		toShow.get(0).add(getInputField(0, this.x, this.y, fWidth, fHeight, 1));
		int idx = 0;
		for(int i=1;i<edited.getAttributes().size();++i) {
			if(findGroup(edited.getAttributes().get(i).getName()).equals(lastName)) {
				if(toShow.get(idx).size() == 1)
					toShow.get(idx).get(0).setWidth(fWidth*8/10);
				toShow.get(idx).add(getInputField(i, this.x, this.y+toShow.get(idx).size()*fHeight, fWidth*8/10, fHeight, 1));
			}else {
				toShow.add(new ArrayList<GUIObject>());
				idx++;
				lastName = findGroup(edited.getAttributes().get(i).getName());
				toShow.get(idx).add(getInputField(i, this.x, this.y+idx*fHeight, fWidth, fHeight, 1));
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
	
	private InputField getInputField(int index, int x, int y, int w, int h, int type) {
		String attrName = edited.getAttributes().get(index).getName();
		String value = edited.getAttributes().get(index).getValue();
		InputField temp;
		
		switch (getIFType(attrName)) {
		case ColorField:
			temp = new ColorField(attrName, x, y, w, h, type);
			break;
		case NumberField:
			temp = new NumberField(attrName, x, y, w, h, type);
			break;
		default:
			temp = new InputField(attrName, x, y, w, h, type);
			break;
		}
		
		temp.setValue(value);
		temp.setContainer(this);
		
		return temp;
	}
	
	private InputFieldType getIFType(String attrName) {
		if(attrName.equals("color") || attrName.equals("background-color")) {
			return InputFieldType.ColorField;
		}else if(findGroup(attrName).equals("margin") || attrName.equals("width") || attrName.equals("height") || attrName.equals("font-size")) {
			return InputFieldType.NumberField;
		}
		return InputFieldType.InputField;
	}
	
	public void checkValues() {
		if(edited==null)
			return;
		int idx = 0;
		for(List<GUIObject> list : toShow) {
			for(GUIObject field : list) {
				if(!((InputField)field).getText().equals(edited.getAttributeValue(idx))) {
					edited.setAttributeValue(idx,((InputField)field));
					((InputField)field).setEditing(false);
				}
				idx++;
			}
		}
	}
	
	@Override
	public void update(int x, int y, int width, int height, WorkingScreen ws) {
		super.update(x, y, width, height, ws);
		this.toShow.clear();
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

	public PageElement getEdited() {
		return edited;
	}

	public void setEdited(PageElement edited) {
		this.edited = edited;
	}

	public List<Attribute> getElementAttributes() {
		return edited.getAttributes();
	}
	
	public void setElementAttributes(PageElement element) {
		this.setEdited(element);
		
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
