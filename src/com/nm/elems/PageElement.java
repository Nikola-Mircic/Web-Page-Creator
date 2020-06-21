package com.nm.elems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nm.elems.tagsystem.Tag;

/*
 * Class: com.nm.elems.PageElement
 * Superclass :
 * Used for: organizing data of single element on a page
 */

public class PageElement {
	private Tag elementTag;
	private List<Attribute> attributes;
	private List<PageElement> childs;
	
	private int x,y;
	private int width,height;
	private BufferedImage img;
	
	public PageElement(String tagname) {
		this.childs = new ArrayList<PageElement>();
		this.elementTag = generateTag(tagname);
		this.attributes = elementTag.getAttributes();
		//drawContent();
	}
	
	public PageElement(Tag tag) {
		this.childs = new ArrayList<PageElement>();
		this.elementTag = tag;
		this.attributes = elementTag.getAttributes();
		//drawContent();
	}
	
	private Tag generateTag(String tagname) {
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
	
	private void drawContent() {
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(getColor(getAttributeValue("background-color")));
		
		g.fillRect(0, 0, width, height);
	}

	private Color getColor(String attribute) {
		if(attribute.length()==0)
			return new Color(0, 0, 0, 0);
		else {
			
			String source = attribute.substring(attribute.indexOf('('), attribute.indexOf(')'));
			int r = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int g = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int b = Integer.parseInt(source.substring(0,source.indexOf(',')));
			source = source.substring(source.indexOf(',')+1);
			int a = Integer.parseInt(source);
			return new Color(r, g, b, a);
		}
	}
	
	public Attribute getAttribute(String attrName) {
		Attribute temp;
		for(Iterator<Attribute> itr = attributes.iterator();itr.hasNext();) {
			temp = itr.next();
			if(temp.getName().equals(attrName))
				return temp;
		}
		return null;
	}
	
	public String getAttributeValue(String attrName) {
		String value = "";
		Attribute temp;
		for(Iterator<Attribute> itr = attributes.iterator();itr.hasNext();) {
			temp = itr.next();
			if(temp.getName().equals(attrName)) {
				value = temp.getValue();
				break;
			}	
		}
		return value;
	}
	
	private void generateDefaultAttributes(Tag tag) {
		switch (tag) {
		case BOX:
			
			break;

		default:
			break;
		}
	}
	
	public void addElement(PageElement newElement) {
		childs.add(newElement);
	}

	public List<Attribute> getAttributes() {
		return attributes;
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

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tag getElementTag() {
		return elementTag;
	}

	public void setElementTag(Tag elementTag) {
		this.elementTag = elementTag;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
