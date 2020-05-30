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

import com.nm.elems.Attribute;
import com.nm.wpc.screen.WorkingScreen;

public class PageEditor extends Editor {
	private static final long serialVersionUID = 1L;
	
	private List<Attribute> pageAttributes;
	private Color bckg;
	
	public PageEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		this.setPageAttributes(new ArrayList<Attribute>());
		bckg = new Color(118, 0, 114);
		drawContent(width, height);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		bckg = new Color(78, 146, 74);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = new Color(118, 186, 114);
		drawContent(width, height);
	}

	public List<Attribute> getPageAttributes() {
		return pageAttributes;
	}

	public void setPageAttributes(List<Attribute> pageAttributes) {
		this.pageAttributes = pageAttributes;
	}
}
