/*
 * Class: com.nm.wpc.editor.ElementEditor
 * Superclass : com.nm.editor.Editor
 * Used for: allows you to change and dispalys attributes of single element
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.ElementAttribute;
import com.nm.wpc.screen.*;

public class ElementEditor extends Editor{
	private static final long serialVersionUID = 1L;
	
	private List<ElementAttribute> attributes;
	private Color bckg;
	
	public ElementEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		this.setAttributes(new ArrayList<ElementAttribute>());
		bckg = new Color(185, 186, 189);
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
		bckg = new Color(135, 136, 139);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = new Color(185, 186, 189);
		drawContent(width, height);
	}

	public List<ElementAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ElementAttribute> attributes) {
		this.attributes = attributes;
	}
}
