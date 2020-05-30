package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;

public class InputPanel extends Screen{
	private static final long serialVersionUID = 1L;
	private String TITLE;
	private GUIObject source;
	private GUIObject last;
	
	public InputPanel(GUIObject source, int x, int y) {
		super(0, 0);
		this.x = x;
		this.y = y;
		this.setSource(source);
		
		this.TITLE = "";
		drawContent();
	}
	
	public InputPanel(GUIObject source, int x, int y, int width, int height) {
		super(width, height);
		this.x = x;
		this.y = y;
		this.setSource(source);
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.TITLE = "";
		
		this.drawContent(width, height);
	}
	
	public void drawContent() {
		getDimensions();
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		if(TITLE != "") {
			g.setFont(new Font("",Font.BOLD,15));
			g.drawString(TITLE, 2, 17);
		}
		drawObjects(g);
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
			g.setFont(new Font("",Font.BOLD,15));
			g.drawString(TITLE, 2, 17);
		}
		drawObjects(g);
	}
	
	public void drawChildPanels(Graphics g) {
		for(InputPanel panel : panels) {
			g.drawImage(panel.getContent(), panel.getX(), panel.getY(), null);
			panel.drawChildPanels(g);
		}
	}
	
	public InputPanel getClicked(int x,int y) {
		for(InputPanel panel : panels) {
			int x1 = panel.getX();
			int y1 =  panel.getY();
			int x2 = x1+panel.getW();
			int y2 = y1+panel.getH();
			if(x1<x && x<x2 && y1<y && y<y2) {
				return panel;
			}else {
				return panel.getClicked(x, y);
			}
		}
		return null;
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		this.panelsActivity = false;
		InputPanel temp = null;
		
		temp = getClicked(x, y);
		
		if(temp != null) {
			temp.onMousePressed(x, y);
			this.drawContent(this.width, this.height);
			return;
		}else {
			panels.removeAll(panels);
		}
		
		GUIObject object = checkOnClick(x, y);
		if(object != null) {
			if(object instanceof InputField) {
				object.mousePressed(x, y);
			}else {
				object.mousePressed();
				last = object;
			}
		}
		
		drawContent(this.width,this.height);
	}
	
	@Override
	public void onMouseRelease() {
		if(this.last == null) {
			for(InputPanel panel : panels) {
				panel.onMouseRelease();
			}
		}else {
			last.mouseReleased();
			last = null;
		}
		
		
		drawContent(this.width,this.height);
	}
	
	private void getDimensions() {
		this.height = 50;
		this.width = 180;
		if(!controler.getObjects().isEmpty()) {
			if(this.TITLE=="")
				this.height = controler.getObjects().get(controler.getObjects().size()-1).getY()-this.y+controler.getObjects().get(controler.getObjects().size()-1).getHeight();
			else
				this.height += controler.getObjects().get(controler.getObjects().size()-1).getY()-this.y+controler.getObjects().get(controler.getObjects().size()-1).getHeight();
			this.width = controler.getObjects().get(0).getWidth();
			for(GUIObject object:controler.getObjects()) {
				if(object.getWidth()>this.width){
					this.width = object.getWidth();
				}
			}
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

	public GUIObject getLast() {
		return last;
	}

	public void setLast(GUIObject last) {
		this.last = last;
	}
}
