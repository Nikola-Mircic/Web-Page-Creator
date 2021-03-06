/*  Copyright 2020 Nikola Mircic
  
    This file is part of Web Page Creator.

    Web Page Creator is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Web Page Creator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Web Page Creator.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Class: com.nm.wpc.gui.InputField
 * Superclass :  com.nm.wpc.gui.GUIObject
 * Used for: getting text data from user
 */

package com.nm.wpc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class InputField extends GUIObject{
	private String LABEL;
	private String textData;
	
	protected int type;
	private int ptOffset;
	private int ptLenght;
	private int cursorPos;
	
	private int border;
	private int font;
	
	private boolean editing = false;
	private boolean alert = false;
	
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
		
		this.textData = "";
		this.ptOffset = 0;
		this.ptLenght = 0;
		this.type = type;
		this.cursorPos = this.textData.length();
		drawImage();
	}
	
	protected void drawImage() {
		if(width<=0 || height<=0)
			return;
		//Frame drawing
		this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.img.getGraphics();
		border=1;
		font = Math.min(findFontSize(), 20);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//Label drawing
		g.setColor(Color.BLACK);
		if(LABEL != "") {
			if(type==0) {
				g.setFont(new Font("",Font.PLAIN,font));
				g.drawString(this.LABEL, 3, height-(height-font)/2);
			}else if(type==1) {
				g.setFont(new Font("",Font.PLAIN,font));
				g.drawString(this.LABEL, 3, height/2-(height/2-font)/2);
			}
		}
		//Text drawing
		String toDraw = getTextShown();
		if(type == 0) {
			this.textField = new BufferedImage((width-(LABEL.length()/2)*font)-2*border, height-2*border, BufferedImage.TYPE_INT_RGB);
			Graphics g2 = this.textField.getGraphics();
			g2.setColor(new Color(230, 230, 230));
			g2.fillRect(0, 0, this.textField.getWidth(), this.textField.getHeight());
			this.drawBorder(g2, 0, 0, this.textField.getWidth(), this.textField.getHeight());
			g2.setFont(new Font("",Font.PLAIN,font));
			g2.drawString(toDraw, border, height*8/10-2*border);
			if(editing && toDraw != "")
				g2.drawString(toDraw.substring(0,cursorPos-ptOffset)+"_", border, height*8/10-2*border);
			g.drawImage(textField, ((LABEL.length()/2)*font)+border, border, null);
			
		}else {
			this.textField = new BufferedImage(width-2*border, height/2-2*border, BufferedImage.TYPE_INT_RGB);
			Graphics g2 = this.textField.getGraphics();
			g2.setColor(new Color(230, 230, 230));
			g2.fillRect(0, 0, this.textField.getWidth(), this.textField.getHeight());
			this.drawBorder(g2, 0, 0, width-2*border, height/2-2*border);
			g2.setFont(new Font("",Font.PLAIN,font));
			g2.drawString(toDraw, border, height*8/font-border);
			if(editing && toDraw != "")
				g2.drawString(toDraw.substring(0,cursorPos-ptOffset)+"_", border, height*8/font-border);
			g.drawImage(textField, border, height/2, null);
		}
		
		if(this.alert) {
			g.setColor(Color.RED);
			g.drawRect(2*border, 2*border, width-4*border, height-4*border);
		}
		
		this.drawBorder(g,0,0, width-1, height-1);
	}
	
	public int findFontSize() {
		int min=1,max=this.height,curr=0;
		while (min<max) {
			curr=min+(max-min)/2;
			int width = img.getGraphics().getFontMetrics(new Font("Serif",Font.PLAIN,curr)).stringWidth(LABEL);
			if(width>=this.width) {
				max = curr-1;
			}else if(width<this.width) {
				min = curr+1;
			}
		}
		return (curr==0)?20:curr-5;
	}
	
	private String getTextShown() {
		String printingText = textData;
		findMaxCharsNumber();
		if(editing) {
			/*if(textData.indexOf("_") == -1)
				textData = insertChar(textData, '|', cursorPos);*/
			if(cursorPos <=  ptLenght) {
				resizeTextLeft(textData);
			}else if(cursorPos >= (textData.length() - ptLenght)) {
				resizeTextRight(textData);
			}else {
				if(cursorPos > (ptOffset + ptLenght)) {
					ptOffset = cursorPos-1;
				}else {
					ptOffset = cursorPos+1-ptLenght;
				}
			}
		}
		printingText = textData.substring(ptOffset, ptOffset+ptLenght);
		if(textData.indexOf("_") != -1)
			textData = removeChar(textData, cursorPos);
		return printingText;
	}
	
	private void findMaxCharsNumber() {
		if(type == 0)
			this.ptLenght = Math.min(textData.length(), ((width-((LABEL.length()/2)*font))-4*border)/10);
		else
			this.ptLenght = Math.min(textData.length(), (width-2*border)/10);
	}
	
	private void resizeTextLeft(String text) {
		findMaxCharsNumber();
		ptOffset = 0;
	}
	
	private void resizeTextRight(String text) {
		findMaxCharsNumber();
		ptOffset = text.length()-ptLenght;
	}
	
	private String insertChar(String base,char c,int pos) {
		if(pos<0 && pos>base.length())
			return base;
		if(pos == base.length()) {
			return (base+c);
		}
		else if(pos == 0) {
			return (c+base);
		}
		else {
			return (base.substring(0, pos)+c+base.substring(pos));
		}
	}
	
	private String removeChar(String base,int pos) {
		if(pos < 0)
			return base;
		if(pos == base.length()-1)
			return base.substring(0,base.length()-1);
		else if(pos == 0)
			return base.substring(1);
		else
			return (base.substring(0, pos)+base.substring(pos+1));
	}
	
	@Override
	public void mousePressed(int x,int y) {
		if(type == 0) {
			if(x>(this.x+width-((LABEL.length()/2)*font)))
				setEditing(true);
			else 
				setEditing(false);
		}else {
			if(y>(this.y+this.height/2)) {
				setEditing(true);
			}
			else 
				setEditing(false);
		}
	}
	
	public void addLetter(char c) {
		this.textData = insertChar(this.textData, c, cursorPos);
		cursorPos++;
		drawImage();
	}
	
	public void removeLetter() {
		if(cursorPos==0)
			return;
		this.textData = removeChar(textData, cursorPos-1);
		cursorPos--;
		drawImage();
	}
	
	public void moveCursor(int dir) {
		cursorPos+=dir;
		if(cursorPos>textData.length()) {
			cursorPos = textData.length();
		}
		if(cursorPos<0) {
			cursorPos = 0;
		}
		drawImage();
	}
	
	public void setAlert(boolean a) {
		this.alert = a;
		drawImage();
	}

	public String getText() {
		return textData;
	}

	public void setText(String text) {
		this.textData = text;
		ptOffset = 0;
		drawImage();
	}
	
	public InputField setValue(String newValue) {
		this.textData = newValue;
		drawImage();
		return this;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean edit) {
		if(edit) {
			cursorPos = textData.length();
			resizeTextRight(textData);
			editing = true;
		}else {
			resizeTextLeft(textData);
			if(textData.indexOf("_") != -1)
				textData = removeChar(textData, cursorPos);
			editing = false;
		}
		
		drawImage();
	}

	public String getLabel() {
		return LABEL;
	}

	public void setLabel(String label) {
		this.LABEL = label;
	}
	
	@Override
	public void setWidth(int newWidth) {
		this.width = newWidth;
		drawImage();
	}
}
