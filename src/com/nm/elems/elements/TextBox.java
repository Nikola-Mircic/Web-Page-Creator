package com.nm.elems.elements;

import java.awt.Font;
import java.awt.Graphics;

import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;

public class TextBox extends PageElement {
	protected String textData = "TEXT_DATA_TEST";
	protected int ptLenght;
	protected int cursorPos;
	protected boolean editing;
	
	protected String fontFamily;
	protected int fontSize;
	
	public TextBox(String tagname) {
		super(tagname);
		
		//setTextData("");
		setPtLenght(0);
		setEditing(true);
		setCursorPos(0);
	}

	public TextBox(Tag tag) {
		super(tag);
		
		//setTextData("");
		setPtLenght(0);
		setEditing(true);
		setCursorPos(0);
	}
	
	@Override
	public void drawContent() {
		super.drawContent();
		Graphics g = this.img.getGraphics();
		this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
		this.fontFamily = getAttribute("font-family").getValue();
		g.setColor(getColor(getAttributeValue("color")));
		if(textData == null) {
			if(editing)
				g.drawString("_", 0, fontSize);
		}else {
			g.setFont(new Font(fontFamily,Font.PLAIN,fontSize));
			g.drawString(textData, 0, fontSize);
			if(editing)
				g.drawString(textData.substring(0, cursorPos)+"_", 0, fontSize);
		}
	}
	
	protected void findMaxCharsNumber() {
			this.ptLenght = Math.min(textData.length(), (width)/fontSize*2);
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
		this.textData = insertChar(this.textData, c, cursorPos);
		cursorPos++;
		drawContent();
	}
	
	public void removeLetter() {
		if(cursorPos==0)
			return;
		this.textData = removeChar(textData, cursorPos-1);
		cursorPos--;
		drawContent();
	}
	
	public void moveCursor(int dir) {
		cursorPos+=dir;
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
