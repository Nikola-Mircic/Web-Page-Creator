package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.nm.wpc.gui.GUIObject;

public class InputPanel extends Screen{
	private static final long serialVersionUID = 1L;
	private String TITLE;
	private Graphics g;
	private GUIObject source;
	
	public InputPanel(GUIObject source,int width,int height) {
		this.setSource(source);
		this.width = width;
		this.height = height;
		this.objects = new ArrayList<GUIObject>();
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.TITLE = "";
		
		this.drawContent(width, height);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		if(TITLE != "") {
			g.setFont(new Font("",Font.BOLD,width/this.TITLE.length()*2));
			g.drawString(TITLE, 2, width/this.TITLE.length()*2);
		}
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String title) {
		this.TITLE = title;
		drawContent(width, height);
	}

	public GUIObject getSource() {
		return source;
	}

	public void setSource(GUIObject source) {
		this.source = source;
	}
}
