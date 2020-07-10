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
		PageElement temp = null,temp2 = null;
		
		for(PageElement elem:elements) {
			temp2 = elem.findSelectedElement(x, y);
			if(temp2!=null) {
				temp = temp2;
			}
		}
		
		return temp;
	}
	
	public void deleteElement(PageElement delete) {
		PageElement toDelete = null;
		for(PageElement element : elements) {
			if(element.equals(delete)) {
				toDelete = element;
				break;
			}else if(element.deleteElement(delete))
				return;
		}
		if(toDelete!=null) {
			elements.addAll(toDelete.getChilds());
			elements.remove(toDelete);
		}
			
	}
	
	public void drawElements(Graphics g) {
		for(PageElement temp : elements) {
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