package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.editor.option.Option;

public class Button extends GUIObject{
	private Option option;
	private Color bckg;
	
	public Button(int x,int y,int width,int height,Option option) {
		super(x,y,width,height);
		if(width>=(option.getOptName().length()/2*height))
			setWidth(width);
		else
			setWidth(option.getOptName().length()/2*height+15);
		this.option = option;
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
		g.setFont(new Font("",Font.PLAIN,height));
		g.drawString(this.option.getOptName(), 3, height-2*border);
	}
	
	@Override
	public void onClick() {
		option.make(this);
	}
}
