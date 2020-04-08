package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.editor.option.Option;

public class Button extends GUIObject{
	private Option option;
	private Color bckg;
	private GUIObject guiObject;
	
	public Button(int x,int y,int width,int height,Option option) {
		super(x,y,width,height);
		this.option = option;
		bckg = Color.WHITE;
		createImage();
	}
	
	public Button(String title,int x,int y,int width,int height,Option option) {
		super(x,y,width,height);
		this.option = option;
		this.option.setOptName(title);
		bckg = Color.WHITE;
		createImage();
	}
	
	private void createImage() {
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.img.getGraphics();
		int border = (Math.min(height,width)+20)/20;
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		this.drawBorder(g,0,0,width,height);
		g.setFont(new Font("",Font.PLAIN,20));
		g.drawString(this.option.getOptName(), 3, height-2*border);
	}
	
	public void fitText(int method) {
		if(method == 1) {
			if(width>=(option.getOptName().length()*10))
				setWidth(width);
			else
				setWidth(option.getOptName().length()*10+15);
		}else if(method == 2) {
			int idx = width/10;
			for(int i=1;i*idx<this.option.getOptName().length();++i) {
				option.setOptName(option.getOptName().substring(0,i*idx+1)+"\n"+option.getOptName().substring(i*idx+1));
			}
		}
		createImage();
		
	}
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
	
	public GUIObject getGuiObject() {
		return guiObject;
	}

	public Button setGuiObject(GUIObject guiObject) {
		this.guiObject = guiObject;
		return this;
	}

	@Override
	public void mousePressed() {
		this.bckg = new Color(230,230,230);
		this.createImage();
		option.make(this);
	}

	@Override
	public void mouseReleased() {
		this.bckg = Color.WHITE;
		this.createImage();
	}
}
