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

package com.nm.elems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.attribute.*;
import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.gui.InputField;

/*
 * Class: com.nm.elems.PageElement
 * Superclass :
 * Used for: organizing data of single element on a page
 */

public class PageElement {
	public AttributeList attributes;
	
	protected Tag elementTag;
	protected List<PageElement> childs;
	
	protected PageElement parentElement;
	protected PageElement previousElement;
	
	protected int x,y;
	protected int offsetX,offsetY;
	protected int width,height;
	protected BufferedImage img;
	
	public PageElement() {
	}
	
	public PageElement(String tagname) {
		this.childs = new ArrayList<PageElement>();
		this.elementTag = generateTag(tagname);
		this.attributes = new ElementAttributeList(elementTag);
		
		setX(0);
		setY(0);
		
		if(tagname.indexOf(' ')!=-1) {
			String temp = tagname.substring(tagname.indexOf("style=")+7);
			temp = temp.substring(0,temp.indexOf('\"'));
			Attribute tempAttr = null;
			while(temp.indexOf("; ")!=-1) {
				tempAttr = getAttribute(temp.substring(0,temp.indexOf(":")));
				if(tempAttr!=null)
					setAttributeValue(attributes.indexOf(tempAttr), temp.substring(temp.indexOf(":")+1,temp.indexOf(tempAttr.getDefaultUnit()+";")));
				temp = temp.substring(temp.indexOf("; ")+2);
			}
		}
		
		drawContent();
	}
	
	public PageElement(Tag tag) {
		this.childs = new ArrayList<PageElement>();
		this.elementTag = tag;
		this.attributes = new ElementAttributeList(elementTag);
		
		setX(0);
		setY(0);
		
		drawContent();
	}
	
	protected Tag generateTag(String tagname) {
		if(tagname.indexOf(' ')!=-1) {
			tagname = tagname.substring(0,tagname.indexOf(" "))+">"+tagname.substring(tagname.lastIndexOf("<"));
		}
		Tag temp = null;
		Tag[] tags = Tag.values();
		for(Tag tag:tags) {
			if(tag.getTagname().equals(tagname)) {
				temp = tag;
				break;
			}
		}
		
		return temp;
	}
	
	public void drawContent() {
		this.width = Math.max(Integer.parseInt(getAttributeValue("width")),30);
		this.height = Math.max(Integer.parseInt(getAttributeValue("height")),20);
		this.img = new BufferedImage(scale(this.width), scale(this.height), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(getColor(getAttributeValue("background-color")));
		g.fillRect(0, 0, scale(this.width), scale(this.height));
	}

	protected Color getColor(String attribute) {
		if(attribute.length()==0)
			return new Color(0, 0, 0, 0);
		else {
			String color[] = attribute.substring(attribute.indexOf('(')+1,attribute.indexOf(')')).split(",");
			int r,g,b,a;
			for(int i=0;i<4;++i) {
				if(color[i].equals(""))
					color[i] = "0";
				else if(Float.parseFloat(color[i])<0)
					color[i]="0";
				else {
					if(Float.parseFloat(color[i])>255 && i<3)
						color[i]="255";
					else if(Float.parseFloat(color[i])>1.0 && i==3)
						color[i]="1.0";
				}
			}
			r = Integer.parseInt(color[0]);
			g = Integer.parseInt(color[1]);
			b = Integer.parseInt(color[2]);
			a = (int)(Float.parseFloat(color[3])*255);
			
			return new Color(r, g, b, a);
		}
	}
	
	public Attribute getAttribute(String attrName) {
		return attributes.getAttribute(attrName);
	}
	
	public String getAttributeValue(int index) {
		return attributes.getAttributeValue(index);
	}
	
	public String getAttributeValue(String attrName) {
		return attributes.getAttributeValue(attrName);
	}
	
	public void setAttributeValue(int index,InputField field) {
		attributes.setAttributeValue(index, field);
		
		Attribute temp = attributes.get(index);
		
		if(temp.getName().equals("margin-top")) {
			changeOffsetY(Integer.parseInt(temp.getValue()));
		}
		if(temp.getName().equals("margin-left"))
			changeOffsetX(Integer.parseInt(temp.getValue()));
		
		drawContent();
	}
	
	public void setAttributeValue(int index,String newValue) {
		attributes.setAttributeValue(index, newValue);
		
		Attribute temp = attributes.get(index);
		
		if(temp.getName().equals("margin-top")) {
			changeOffsetY(Integer.parseInt(temp.getValue()));
		}
		if(temp.getName().equals("margin-left"))
			changeOffsetX(Integer.parseInt(temp.getValue()));
		
		drawContent();
	}
	
	public PageElement findSelectedElement(int x,int y) {
		PageElement temp = null,temp2 = null;
		
		for(PageElement elem : childs) {
			temp2 = elem.findSelectedElement(x, y);
			if(temp2!=null) {
				temp = temp2;
			}
		}
		
		if(isClicked(x, y) && temp == null)
			return this;
		
		return temp;
	}
	
	public boolean deleteElement(PageElement delete) {
		PageElement toDelete = null;
		for(PageElement element : childs) {
			if(element.equals(delete)) {
				toDelete = element;
				break;
			}else if(element.deleteElement(delete))
				return true;
		}
		if(toDelete!=null) {
			for(PageElement element : toDelete.childs)
				if(childs.size()==1) {
					element.setParentElement(this);
					element.changePreviousElement(null);
					element.setX(toDelete.x);
					element.setY(toDelete.y);
					element.setOffsetX(element.offsetX+toDelete.offsetX);
					element.setOffsetY(element.offsetY+toDelete.offsetY);
					childs.add(element);
				}else {
					element.setParentElement(this);
					element.changePreviousElement(childs.get(childs.size()-1));
					element.setX(toDelete.x);
					element.setY(toDelete.y);
					element.setOffsetX(element.offsetX+toDelete.offsetX);
					element.setOffsetY(element.offsetY+toDelete.offsetY);
					childs.add(element);
				}
			childs.remove(toDelete);
			return true;
		}
		return false;
	}
	
	public boolean isClicked(int xPos,int yPos) {
		xPos-=scale(this.offsetX);
		yPos-=scale(this.offsetY);
		
		return (xPos>scale(this.x) && xPos<scale(this.x + this.width) && yPos>scale(this.y) && yPos<scale(this.y+this.height));
	}
	
	public byte getActionCode(int xPos,int yPos) {
		xPos-=scale(this.offsetX);
		yPos-=scale(this.offsetY);
		
		if(scale(this.x)-10<=xPos && xPos<=scale(this.x)+10 && scale(this.y)-10<=yPos && yPos<=scale(this.y)+10)
			return 1;
		if(scale(this.x)+scale(width)-10<=xPos && xPos<=scale(this.x)+scale(width)+10 && scale(this.y)-10<=yPos && yPos<=scale(this.y)+10)
			return 2;
		if(scale(this.x)+scale(width)-10<=xPos && xPos<=scale(this.x)+scale(width)+10 && scale(this.y)+scale(height)-10<=yPos && yPos<=scale(this.y)+scale(height)+10)
			return 3;
		if(scale(this.x)-10<=xPos && xPos<=scale(this.x)+10 && scale(this.y)+scale(height)-10<=yPos && yPos<=scale(this.y)+scale(height)+10)
			return 4;
		if(isClicked(xPos+scale(this.offsetX), yPos+scale(this.offsetY)))
			return 0;
		
		return -1;
	}
	
	public void drawContent(Graphics g) {
		g.drawImage(this.img, scale(this.x)+scale(this.offsetX), scale(this.y)+scale(this.offsetY), null);
		for(PageElement temp : childs) {
			temp.drawContent(g);
		}
	}
	
	public void addElement(PageElement newElement) {
		if(this.childs.size()>0) {
			newElement.setParentElement(this, childs.get(childs.size()-1));
		}else {
			newElement.setParentElement(this, null);
		}
		childs.add(newElement);
	}
	
	public PageElement getParentElement() {
		return parentElement;
	}

	public void setParentElement(PageElement parent) {
		this.parentElement = parent;
	}
	
	public void setParentElement(PageElement parent,PageElement previous) {
		this.parentElement = parent;
		if(previous==null) {
			int offset = parent.getX()+parent.getOffsetX();
			setX(offset);
				
			offset = parent.getY()+parent.getOffsetY();
			setY(offset);
		}else {
			setPreviousElement(previous);
		}
	}

	public PageElement getPreviousElement() {
		return previousElement;
	}
	
	public void changePreviousElement(PageElement previous) {
		this.previousElement = previous;
	}

	public void setPreviousElement(PageElement previous) {
		this.previousElement = previous;
		String position = getAttributeValue("position");
		if(previous!=null) {
			int offset;
			switch (position) {
			case "absolute":
				offset = previous.getX();
				setX(offset);
				
				offset = previous.getY();
				setY(offset);
				break;
			default:
				offset = previous.getX();
				setX(offset);
				
				offset = previous.getY()+previous.getHeight();
				setY(offset);
				break;
			}
		}
	}
	
	public String getContent() {
		String tag = this.elementTag.getTagname();
		String openTag = tag.substring(0, tag.indexOf("</"));
		String closeTag = tag.substring(tag.indexOf("</"));
		String childsContent = "";
		String styles = "style=\"";
		
		for(Attribute attr : attributes.getAttributes()) {
			styles+=attr.getName()+":"+attr.getValue()+attr.getDefaultUnit()+"; ";
		}
		styles+="\"";
		for(PageElement element:childs) {
			childsContent+=element.getContent();
		}
		
		openTag = openTag.substring(0, openTag.indexOf(">"))+" "+styles+" >";
		
		return openTag+childsContent+closeTag;
	}
	
	public List<Attribute> getAttributes() {
		return this.attributes.getAttributes();
	}
	
	public AttributeList getAttributesList() {
		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes.setAttributes(attributes);
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

	public int getOffsetX() {
		return offsetX;
	}
	
	public void loadOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetX(int offsetX) {
		String newMargin = Integer.toString((Integer.parseInt(getAttribute("margin-left").getValue())+(int)((offsetX-this.offsetX))));
		getAttribute("margin-left").setValue(newMargin);
		
		changeOffsetX(offsetX);
	}
	
	public void changeOffsetX(int offsetX) {
		for(PageElement element : childs) {
			element.setX(element.getX()+offsetX-this.offsetX);
		}
		
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void loadOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public void setOffsetY(int offsetY) {
		String newMargin = Integer.toString((Integer.parseInt(getAttribute("margin-top").getValue())+(int)((offsetY-this.offsetY))));
		getAttribute("margin-top").setValue(newMargin);
		
		changeOffsetY(offsetY);
	}
	
	public void changeOffsetY(int offsetY) {
		for(PageElement element : childs) {
			element.setY(element.getY()+offsetY-this.offsetY);
		}
		
		this.offsetY = offsetY;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		if(width<30)
			this.width = 30;
		this.getAttribute("width").setValue(Integer.toString(this.width));
		this.drawContent();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		if(height<30)
			this.height = 30;
		this.getAttribute("height").setValue(Integer.toString(this.height));
		this.drawContent();
	}
	
	public void stopFocus() {}
	
	public void startFocus() {}

	public Tag getElementTag() {
		return elementTag;
	}

	public void setElementTag(Tag elementTag) {
		this.elementTag = elementTag;
	}

	public List<PageElement> getChilds() {
		return childs;
	}

	public void setChilds(List<PageElement> childs) {
		this.childs = childs;
	}

	public BufferedImage getImg() {
		drawContent();
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public int scale(double x) {
		return (int)(0.8*x);
	}
	
	public int scale(int x) {
		return (int)(0.8*x);
	}
}
