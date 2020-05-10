package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.nm.elems.*;

public class ElementEditor extends Editor{
	private PageElement element = null;
	
	public ElementEditor(int w,int h) {
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		drawImage();
	}
	
	@Override
	public void drawImage() {
		if(element != null) {
			
		}else{
			Graphics g = this.image.getGraphics();
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, this.image.getWidth(), this.image.getHeight());
		}
	}
	                                  
	public void makeEditor(PageElement newElement) {
		this.element = newElement;
		this.opts = newElement.getOpts();
		drawImage();
	}
}
