package com.nm.elems.elements;

import java.awt.Font;
import java.awt.Graphics;

import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;

public class TextBox extends PageElement {
	protected String textData;
	protected int ptLenght;
	protected int cursorPos;
	protected boolean editing;
	protected int lines;
	
	protected String fontFamily;
	protected int fontSize;
	
	protected int upLett=0,lowLett=0;
	
	public TextBox(String tagname) {
		super(tagname);
		
		setTextData("");
		setPtLenght(0);
		setEditing(true);
		setCursorPos(0);
		this.lines = 1;
		
		this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
		this.fontFamily = getAttributeValue("font-family");
		
		findMaxCharsNumber();
	}

	public TextBox(Tag tag) {
		super(tag);
		
		setTextData("");
		setPtLenght(0);
		setEditing(true);
		setCursorPos(0);
		this.lines = 1;
		
		this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
		this.fontFamily = getAttributeValue("font-family");
		
		findMaxCharsNumber();
	}
	
	@Override
	public void drawContent() {
		super.drawContent();
		Graphics g = this.img.getGraphics();
		this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
		this.fontFamily = getAttribute("font-family").getValue();
		findMaxCharsNumber();
		g.setColor(getColor(getAttributeValue("color")));
		if(textData == null) {
			if(editing)
				g.drawString("_", 0, fontSize);
		}else {
			g.setFont(new Font(fontFamily,Font.PLAIN,fontSize));
			int idx1,idx2;
			for(int i=0;i<lines;++i) {
				idx1 = i*ptLenght;
				idx2 = i*ptLenght+Math.min(ptLenght, textData.length()-i*ptLenght);
				g.drawString(textData.substring(idx1,idx2), 0, fontSize*(i+1));
			}
		}
		if(editing) {
			int cursorLine = cursorPos/ptLenght;
			g.drawString(textData.substring(ptLenght*cursorLine, cursorPos)+"_", 0, fontSize*(cursorLine+1));
		}
	}
	
	protected void findMaxCharsNumber() {
			if(fontSize!=0 && width != 0)
				this.ptLenght = (width)/(fontSize/2);
	}
	
	protected String insertChar(String base,char c,int pos) {
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
	
	protected String removeChar(String base,int pos) {
		if(pos < 0)
			return base;
		if(pos == base.length()-1)
			return base.substring(0,base.length()-1);
		else if(pos == 0)
			return base.substring(1);
		else
			return (base.substring(0, pos)+base.substring(pos+1));
	}
	
	public void addLetter(char c) {
		findMaxCharsNumber();
		if(Character.isUpperCase(c))
			upLett++;
		else
			lowLett++;
		if(textData.length()==lines*ptLenght) {
			lines++;
			if(this.height<lines*fontSize)
				getAttribute("height").setValue(""+lines*fontSize);
		}
		this.textData = insertChar(this.textData, c, cursorPos);
		cursorPos++;
		drawContent();
	}
	
	public void removeLetter() {
		findMaxCharsNumber();
		if(cursorPos==0)
			return;
		if(textData.length()<lines*ptLenght && lines != 1) {
			this.height = this.height/lines*(lines-1);
			lines--;
		}
		if(Character.isUpperCase(textData.charAt(cursorPos-1)))
			upLett--;
		else
			lowLett--;
		this.textData = removeChar(textData, cursorPos-1);
		cursorPos--;
		drawContent();
	}
	
	public void moveCursor(int dirX,int dirY) {
		cursorPos+=dirX;
		cursorPos+=dirY*ptLenght;
		if(cursorPos>textData.length()) {
			cursorPos = textData.length();
		}
		if(cursorPos<0) {
			cursorPos = 0;
		}
		drawContent();
	}
	
	@Override
	public void stopFocus() {
		this.editing = false;
		drawContent();
	}
	
	@Override
	public void startFocus() {
		this.editing = true;
		drawContent();
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		findMaxCharsNumber();
		lines = textData.length()/ptLenght;
		if(this.height<lines*fontSize) {
			String temp = textData.substring(0,height/fontSize*ptLenght);
			cursorPos -= textData.length()-temp.length();
			textData = temp;
		}
	}
	
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		findMaxCharsNumber();
		lines = textData.length()/ptLenght;
		if(this.height<lines*fontSize) {
			String temp = textData.substring(0,height/fontSize*ptLenght);
			cursorPos -= textData.length()-temp.length();
			textData = temp;
		}
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public int getPtLenght() {
		return ptLenght;
	}

	public void setPtLenght(int ptLenght) {
		this.ptLenght = ptLenght;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
	public int getCursorPos() {
		return cursorPos;
	}

	public void setCursorPos(int cursorPos) {
		this.cursorPos = cursorPos;
	}

}