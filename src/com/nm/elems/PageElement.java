package com.nm.elems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.editor.WorkingPane;
import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.window.Window;

/*
 * Class: com.nm.elems.PageElement
 * Superclass :
 * Used for: organizing data of single element on a page
 */

public class PageElement {
	protected Tag elementTag;
	protected List<Attribute> attributes;
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
		this.attributes = generateAttributes(elementTag.getBitmask());
		generateDefaultAttributes(elementTag);
		
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
		this.attributes = generateAttributes(elementTag.getBitmask());
		
		generateDefaultAttributes(elementTag);
		
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
	
	//Generate list of attributes from given bitmask
	protected List<Attribute> generateAttributes(int bitmask){
		FileManager files = new FileManager();//for getting all attributes from attribute.dat file
		Attribute[] attrList = files.getAttributes();//storing attributes to list
		List<Attribute> temp = new ArrayList<Attribute>();//return data
		int d;//for storing position of the byte
		for(int i=0;i<attrList.length;++i) {
			d = attrList.length-i-1;
			if((bitmask & (1<<d))>>d == 1)
				temp.add(attrList[i]);
		}
		
		return temp;
	}
	
	protected void drawContent() {
		this.width = Integer.parseInt(getAttributeValue("width"));
		this.height = Integer.parseInt(getAttributeValue("height"));
		this.img = new BufferedImage((int)(0.8*this.width), (int)(0.8*this.height), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(getColor(getAttributeValue("background-color")));
		g.fillRect(0, 0, (int)(0.8*this.width), (int)(0.8*this.height));
	}

	protected Color getColor(String attribute) {
		if(attribute.length()==0)
			return new Color(0, 0, 0, 0);
		else {
			
			String source = attribute.substring(attribute.indexOf('(')+1, attribute.indexOf(')'));
			int r = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int g = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int b = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int a = (int)(Float.parseFloat(source)*255);
			return new Color(r, g, b, a);
		}
	}
	
	public Attribute getAttribute(String attrName) {
		for(Attribute temp : attributes) {
			if(temp.getName().equals(attrName))
				return temp;
		}
		return null;
	}
	
	public String getAttributeValue(int index) {
		if(index>=attributes.size() || index<0)
			return "";
		return attributes.get(index).getValue();
	}
	
	public String getAttributeValue(String attrName) {
		for(Attribute temp : attributes){
			if(temp.getName().equals(attrName)) {
				return temp.getValue();
			}	
		}
		return "";
	}
	
	public void setAttributeValue(int index,String newValue) {
		if(index>=attributes.size() || index<0)
			return;
		attributes.get(index).setValue(newValue);
		Attribute temp = attributes.get(index);
		switch(temp.getName()) {
			case "position":
					switch (temp.getValue()) {
					case "absulute":
						getAttribute("margin-top").setValue(Integer.toString(this.y));
						getAttribute("margin-left").setValue(Integer.toString(this.x));
						break;
					default:
						//return true;
					}
				break;
			case "margin-top":
				switch (getAttribute("position").getValue()) {
				case "absolute":
					if(temp.getValue().equals(""))
						attributes.get(index).setValue("0");
					this.offsetY = (int)(0.865*Integer.parseInt(temp.getValue()));
					break;

				default:
					break;
				}
				break;
			case "margin-left":
				switch (getAttribute("position").getValue()) {
				case "absolute":
					if(temp.getValue().equals(""))
						attributes.get(index).setValue("0");
					this.offsetX = (int)(0.8*Integer.parseInt(temp.getValue()));
					break;

				default:
					break;
				}
				break;
			default:
				break;
		}
		drawContent();
	}
	
	protected void generateDefaultAttributes(Tag tag) {
		switch (tag) {
			case BOX:
				getAttribute("color").setValue("rgba(0,0,0,1.0)");
				getAttribute("background-color").setValue("rgba(150,150,150,1.0)");
				getAttribute("width").setValue("200");
				getAttribute("height").setValue("100");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-right").setValue("0");
				getAttribute("margin-bottom").setValue("0");
				getAttribute("margin-left").setValue("0");
				getAttribute("padding-top").setValue("0");
				getAttribute("padding-right").setValue("0");
				getAttribute("padding-bottom").setValue("0");
				getAttribute("padding-left").setValue("0");
			break;
			case TEXT_BOX:
				getAttribute("font-size").setValue("20");
				getAttribute("font-family").setValue("Serif");
				getAttribute("color").setValue("rgba(0,0,0,1.0)");
				getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
				getAttribute("width").setValue("250");
				getAttribute("height").setValue("30");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-right").setValue("0");
				getAttribute("margin-bottom").setValue("0");
				getAttribute("margin-left").setValue("0");
				getAttribute("padding-top").setValue("0");
				getAttribute("padding-right").setValue("0");
				getAttribute("padding-bottom").setValue("0");
				getAttribute("padding-left").setValue("0");
				break;
			case BODY:
				getAttribute("color").setValue("rgba(0,0,0,1.0)");
				getAttribute("background-color").setValue("rgba(136,225,247,1.0)");
				getAttribute("width").setValue("1000");
				getAttribute("height").setValue("700");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-right").setValue("0");
				getAttribute("margin-bottom").setValue("0");
				getAttribute("margin-left").setValue("0");
				getAttribute("padding-top").setValue("0");
				getAttribute("padding-right").setValue("0");
				getAttribute("padding-bottom").setValue("0");
				getAttribute("padding-left").setValue("0");
				break;
			case ANCHOR:
				getAttribute("font-size").setValue("20");
				getAttribute("font-family").setValue("Serif");
				getAttribute("color").setValue("rgba(10,10,200,1.0)");
				getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
				getAttribute("width").setValue("250");
				getAttribute("height").setValue("30");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-right").setValue("0");
				getAttribute("margin-bottom").setValue("0");
				getAttribute("margin-left").setValue("0");
				getAttribute("padding-top").setValue("0");
				getAttribute("padding-right").setValue("0");
				getAttribute("padding-bottom").setValue("0");
				getAttribute("padding-left").setValue("0");
				break;
			default:
				String tagString = tag.toString();
				if(tagString.substring(0,7).equals("HEADING")) {
					int headingType = Integer.parseInt(""+tagString.charAt(8));
					getAttribute("font-size").setValue(Integer.toString(25+(6-headingType)*10));
					getAttribute("font-family").setValue("Times New Roman");
					getAttribute("color").setValue("rgba(255,255,255,1.0)");
					getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
					getAttribute("width").setValue(Integer.toString(300+(6-headingType)*25));
					getAttribute("height").setValue(Integer.toString(35+(6-headingType)*10));
					getAttribute("margin-top").setValue("0");
					getAttribute("margin-right").setValue("0");
					getAttribute("margin-bottom").setValue("0");
					getAttribute("margin-left").setValue("0");
					getAttribute("padding-top").setValue("0");
					getAttribute("padding-right").setValue("0");
					getAttribute("padding-bottom").setValue("0");
					getAttribute("padding-left").setValue("0");
				}
				break;
		}
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
		xPos-=offsetX;
		yPos-=offsetY;
		return (xPos>this.x && xPos<(this.x + this.width) && yPos>this.y && yPos<(this.y+this.height));
	}
	
	public byte getActionCode(int xPos,int yPos) {
		xPos-=this.offsetX;
		yPos-=this.offsetY;
		
		if(this.x-10<=xPos && xPos<=this.x+10 && this.y-10<=yPos && yPos<=this.y+10)
			return 1;
		if(this.x+(int)(0.8*width)-10<=xPos && xPos<=this.x+(int)(0.8*width)+10 && this.y-10<=yPos && yPos<=this.y+10)
			return 2;
		if(this.x+(int)(0.8*width)-10<=xPos && xPos<=this.x+(int)(0.8*width)+10 && this.y+(int)(0.8*height)-10<=yPos && yPos<=this.y+(int)(0.8*height)+10)
			return 3;
		if(this.x-10<=xPos && xPos<=this.x+10 && this.y+(int)(0.8*height)-10<=yPos && yPos<=this.y+(int)(0.8*height)+10)
			return 4;
		if(isClicked(xPos+offsetX, yPos+offsetY))
			return 0;
		
		return -1;
	}
	
	public void drawContent(Graphics g) {
		g.drawImage(this.img, this.x+this.offsetX, this.y+this.offsetY, null);
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
		
		for(Attribute attr : attributes) {
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
		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
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
		String newMargin = Integer.toString((int)((Integer.parseInt(getAttribute("margin-left").getValue())+offsetX-this.offsetX)*0.8));
		getAttribute("margin-left").setValue(newMargin);
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void loadOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public void setOffsetY(int offsetY) {
		String newMargin = Integer.toString((int)((Integer.parseInt(getAttribute("margin-top").getValue())+offsetY-this.offsetY)*0.865));
		getAttribute("margin-top").setValue(newMargin);
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
}
