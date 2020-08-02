package com.nm.elems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.filesystem.FileManager;

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
	
	protected int x,y;
	protected int width,height;
	protected BufferedImage img;
	
	public PageElement(String tagname) {
		this.childs = new ArrayList<PageElement>();
		this.elementTag = generateTag(tagname);
		this.attributes = generateAttributes(elementTag.getBitmask());
		generateDefaultAttributes(elementTag);
		setX(0);
		setY(0);
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
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(getColor(getAttributeValue("background-color")));
		g.fillRect(0, 0, width, height);
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
					this.y = Integer.parseInt(temp.getValue());
					break;

				default:
					break;
				}
				break;
			case "margin-left":
				switch (getAttribute("position").getValue()) {
				case "absolute":
					this.x = Integer.parseInt(temp.getValue());
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
				element.setParent(this);
			childs.addAll(toDelete.childs);
			childs.remove(toDelete);
			return true;
		}
		return false;
	}
	
	public boolean isClicked(int x,int y) {
		return (x>this.x && x<(this.x + this.width) && y>this.y && y<(this.y+this.height));
	}
	
	public byte getActionCode(int x,int y) {
		if(this.x-10<=x && x<=this.x+10 && this.y-10<=y && y<=this.y+10)
			return 1;
		if(this.x+width-10<=x && x<=this.x+width+10 && this.y-10<=y && y<=this.y+10)
			return 2;
		if(this.x+width-10<=x && x<=this.x+width+10 && this.y+height-10<=y && y<=this.y+height+10)
			return 3;
		if(this.x-10<=x && x<=this.x+10 && this.y+height-10<=y && y<=this.y+height+10)
			return 4;
		if(isClicked(x, y))
			return 0;
		return -1;
	}
	
	public void drawContent(Graphics g) {
		g.drawImage(this.img, this.x, this.y, null);
		PageElement temp;
		for(Iterator<PageElement> iter = childs.iterator();iter.hasNext();) {
			temp = iter.next();
			temp.drawContent(g);
		}
	}
	
	public void addElement(PageElement newElement) {
		childs.add(newElement.setParentElement(this));
	}
	
	public PageElement setParentElement(PageElement parent) {
		this.parentElement = parent;
		return this;
	}
	
	public void setParent(PageElement parent) {
		this.parentElement = parent;
	}
	
	public PageElement getParent() {
		return this.parentElement;
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
		switch (getAttribute("position").getValue()) {
		case "absolute":
			getAttribute("margin-left").setValue(Integer.toString(this.x));
			break;

		default:
			break;
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		switch (getAttribute("position").getValue()) {
		case "absolute":
			getAttribute("margin-top").setValue(Integer.toString(this.y));
			break;

		default:
			break;
		}
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
