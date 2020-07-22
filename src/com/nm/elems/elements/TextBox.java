package com.nm.elems.elements;

import java.awt.Font;
import java.awt.Graphics;

import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;

public class TextBox extends PageElement {
	protected String textData = "TEXT_DATA_TEST";
	protected int ptLenght;
	protected boolean editing;
	
	protected String fontFamily;
	protected int fontSize;
	
	public TextBox(String tagname) {
		super(tagname);
		
		//setTextData("");
		setPtLenght(0);
		setEditing(true);
	}

	public TextBox(Tag tag) {
		super(tag);
		
		//setTextData("");
		setPtLenght(0);
		setEditing(true);
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
				g.drawString(textData+"_", 0, fontSize);
		}
	}
	
	private void findMaxCharsNumber() {
			this.ptLenght = Math.min(textData.length(), (width)/10);
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

}
