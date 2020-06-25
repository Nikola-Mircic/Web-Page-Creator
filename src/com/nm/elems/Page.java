package com.nm.elems;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nm.elems.loader.PageLoader;

/*
 * Class: com.nm.elems.Page
 * Superclass :
 * Used for: saves data and convert it to HTML format
 */


public class Page {
	private String TITLE;
	private List<PageElement> elements;
	private String pageContent;
	private String pageHead;
	private String pageBody;
	private String pageLocation;
	
	private PageLoader pl;
	
	public Page() {
		pl = new PageLoader();
		pl.createBlankPage(this);
		
		this.elements = new ArrayList<PageElement>();
	}
	
	public PageElement findSelectedElement(int x,int y) {
		PageElement temp=null,temp2=null;
		for(Iterator<PageElement> iter = elements.iterator();iter.hasNext();) {
			temp = iter.next().findSelectedElement(x, y);
			if(temp!=null)
				temp2 = temp;
		}
		return temp2;
	}
	
	public void drawElements(Graphics g) {
		PageElement temp;
		for(Iterator<PageElement> iter = elements.iterator();iter.hasNext();) {
			temp = iter.next();
			temp.drawContent(g);
		}
	}
	
	public void addElement(PageElement newElement) {
		this.elements.add(newElement);
	}
	
	public String getElementsContent() {
		return "";
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getPageHead() {
		return pageHead;
	}

	public void setPageHead(String pageHead) {
		this.pageHead = pageHead;
	}

	public String getPageBody() {
		return pageBody;
	}

	public void setPageBody(String pageBody) {
		this.pageBody = pageBody;
	}

	public String getLocation() {
		return pageLocation;
	}

	public void setLocation(String pageLocation) {
		this.pageLocation = pageLocation;
	}

	public List<PageElement> getElements() {
		return elements;
	}

	public void setElements(List<PageElement> elements) {
		this.elements = elements;
	}
}