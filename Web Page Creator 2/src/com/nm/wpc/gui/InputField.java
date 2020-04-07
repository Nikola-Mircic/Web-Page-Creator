package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class InputField extends GUIObject{
	private String LABEL;
	private String textData;
	private String printingText;
	
	
	private int type;
	
	//private boolean editing = false;
	
	private BufferedImage textField;
	
	public InputField(int x,int y,int width,int height) {
		super(x,y,width,height);
		this.LABEL = "";
		this.type = 1;
		drawImage();
	}
	
	public InputField(String label,int x,int y,int width,int height,int type) {
		super(x,y,width,height);
		this.LABEL = label;
		this.textData = "abc123";
		this.printingText = this.textData;
		this.type = type;
		drawImage();
	}
	
	private void drawImage() {
		//Frame drawing
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.img.getGraphics();
		int border=0;
		if(type==0)
			border = (height+20)/20;
		else if(type == 1)
			border = (height/2+20)/20;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		this.drawBorder(g,0,0, width, height);
		//Label drawing
		if(LABEL != "") {
			if(type==0) {
				g.setFont(new Font("",Font.PLAIN,20));
				g.drawString(this.LABEL, 3, height-2*border);
			}else if(type==1) {
				g.setFont(new Font("",Font.PLAIN,20));
				g.drawString(this.LABEL, 3, height/2-border);
			}
		}
		//Text drawing
		if(type == 0) {
			this.textField = new BufferedImage((width-((LABEL.length()/2)*20))-4*border, height-4*border, BufferedImage.TYPE_INT_RGB);
			Graphics g2 = this.textField.getGraphics();
			g2.setColor(new Color(230, 230, 230));
			g2.fillRect(0, 0, this.textField.getWidth(), this.textField.getHeight());
			this.drawBorder(g2, 0, 0, (width-((LABEL.length()/2)*height))-4*border, height-4*border);
			g2.setFont(new Font("",Font.PLAIN,20));
			g2.drawString(this.printingText, border, height*8/10-2*border);
			g.drawImage(textField, ((LABEL.length()/2)*20)+2*border, 2*border, null);
			
		}else {
			this.textField = new BufferedImage(width-2*border, height/2-2*border, BufferedImage.TYPE_INT_RGB);
			Graphics g2 = this.textField.getGraphics();
			g2.setColor(new Color(230, 230, 230));
			g2.fillRect(0, 0, this.textField.getWidth(), this.textField.getHeight());
			this.drawBorder(g2, 0, 0, width-2*border, height/2-2*border);
			g2.setFont(new Font("",Font.PLAIN,20));
			g2.drawString(this.printingText, border, height*8/20-border);
			g.drawImage(textField, border, height/2, null);
		}
	}
	
	/*private String resizeText(String text) {
		String s = text;
		int n = text.length();
		if(n/2*20>width) {
		}
		return s;
	}*/
	
	/*@Override
	public void onClick(int x,int y) {
		text="|";
		//editing = true;
		drawImage();
	}*/
	
	public void mousePressed() {
		printingText="|";
		//editing = true;
		drawImage();
	}
	
	public void mouseReleased() {
		printingText = "12345";
		drawImage();
	}

	public String getText() {
		return textData;
	}

	public void setText(String text) {
		this.textData = text;
	}
}
