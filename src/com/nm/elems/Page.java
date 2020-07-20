package com.nm.elems;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.loader.PageLoader;

/*
 * Class: com.nm.elems.Page
 * Superclass :
 * Used for: saves data and convert it to HTML format
 */


public class Page {
	private String TITLE;
	//private List<PageElement> elements;
	private String pageContent;
	private String pageHead;
	private String pageLocation;
	
	private PageElement body;
	
	private PageLoader pl;
	
	public Page() {
		pl = new PageLoader();
		pl.createBlankPage(this);
		
		//this.elements = new ArrayList<PageElement>();
		this.body = new PageElement("<body></body>");
	}
	
	public PageElement findSelectedElement(int x,int y) {
		PageElement temp = body.findSelectedElement(x, y);
		return (temp.equals(body)?null:temp);
	}
	
	public void deleteElement(PageElement delete) {
		body.deleteElement(delete);
	}
	
	public void drawElements(Graphics g) {
		for(PageElement element : body.getChilds())
			element.drawContent(g);
	}
	
	public void addElement(PageElement newElement) {
		body.addElement(newElement);
	}
	
	public String getElementsContent() {
		return "";//FIN
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

	/*public String getPageBody() {
		return pageBody;
	}

	public void setPageBody(String pageBody) {
		this.pageBody = pageBody;
	}*/

	public String getLocation() {
		return pageLocation;
	}

	public void setLocation(String pageLocation) {
		this.pageLocation = pageLocation;
	}

	public List<PageElement> getElements() {
		return body.getChilds();
	}

	/*public void setElements(List<PageElement> elements) {
		body.setChilds(elements);
	}*/
}