package com.nm.elems.elements;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.nm.elems.Attribute;
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
	
	public TextBox(String tagname) {
		super();
		setTextData("");
		
		this.childs = new ArrayList<PageElement>();
		this.elementTag = generateTag(tagname);
		this.attributes = generateAttributes(elementTag.getBitmask());
		generateDefaultAttributes(elementTag);
		
		if(tagname.indexOf(' ')!=-1) {
			String temp = tagname.substring(tagname.indexOf("style=")+7);
			temp = temp.substring(0,temp.indexOf('\"'));
			Attribute tempAttr = null;
			while(temp.indexOf("; ")!=-1) {
				tempAttr = getAttribute(temp.substring(0,temp.indexOf(":")));
				if(tempAttr!=null)
					setAttributeValue(attributes.indexOf(tempAttr), temp.substring(temp.indexOf(":")+1,temp.indexOf(tempAttr.getDefaultUnit()+";")));
				temp = temp.substring(temp.indexOf("; ")+2);
			}
			
			setEditing(false);
		}else {
			setEditing(true);
		}
		
		setPtLenght(0);
		setCursorPos(0);
		this.lines = 1;
		
		this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
		this.fontFamily = getAttributeValue("font-family");
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
	}
	
	@Override
	public void drawContent() {
		super.drawContent();
		Graphics g = this.img.getGraphics();
		if(textData == null) {
			if(editing)
				g.drawString("_", 0, fontSize);
		}else {
			int idx1=0,idx2=0;
			g.setFont(new Font(fontFamily,Font.PLAIN,scale(fontSize)));
			g.setColor(getColor(getAttributeValue("color")));
			for(int i=0;i<lines;++i) {
				ptLenght = findptLenght(g,idx1);
				idx2 = idx1+ptLenght;
				g.drawString(textData.substring(idx1,idx2), 0, scale(fontSize*(i+1)-fontSize/20));
				if(editing && cursorPos >= idx1 && cursorPos <= idx2 && (ptLenght>0 || i==0)) {
					g.drawString(textData.substring(idx1, cursorPos)+"_", 0, scale(fontSize*(i+1)-fontSize/20));
				}
				idx1 = idx2;
			}
		}
	}
	
	protected int findptLenght(Graphics g,int idx) {
		int l=idx,d=textData.length(),s,res=idx;
		while(l<=d) {
			s = l+(d-l)/2;
			if(g.getFontMetrics(g.getFont()).stringWidth(textData.substring(idx,s))<scale(width)) {
				res=s;
				l=s+1;
			}else {
				d=s-1;
			}
		}
		return res-idx;
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
		int len = img.getGraphics().getFontMetrics(new Font(fontFamily, Font.PLAIN, scale(fontSize))).stringWidth(textData);
		if(len>=lines*(scale(width))) {
			lines++;
			if(scale(this.height)<lines*(scale(fontSize)+10))
				changeHeight(lines*(fontSize+10));
		}
		cursorPos++;
		drawContent();
	}
	
	public void removeLetter() {
		if(cursorPos==0)
			return;
		int len = img.getGraphics().getFontMetrics(new Font(fontFamily, Font.PLAIN, scale(fontSize))).stringWidth(textData);
		if(len<(lines-1)*scale(width)) {
			changeHeight(this.height/lines*(--lines));
		}
		this.textData = removeChar(textData, cursorPos-1);
		cursorPos--;
		drawContent();
	}
	
	public void moveCursor(int dirX,int dirY) {
		cursorPos+=dirX;
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
	public void setAttributeValue(int index, String newValue) {
		super.setAttributeValue(index, newValue);
		if(attributes.get(index).getName().equals("font-size") || attributes.get(index).getName().equals("font-family")) {
			this.fontSize = Integer.parseInt(getAttributeValue("font-size"));
			this.fontFamily = getAttributeValue("font-family");
			int len = img.getGraphics().getFontMetrics(new Font(fontFamily, Font.PLAIN, scale(fontSize))).stringWidth(textData);
			lines = len/scale(width)+1;
			if(scale(this.height)<lines*scale(fontSize)) {
				changeHeight(lines*fontSize+10);
			}
		}
		drawContent();
	}
	
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		int len = img.getGraphics().getFontMetrics(new Font(fontFamily, Font.PLAIN, scale(fontSize))).stringWidth(textData);
		lines = len/scale(width)+1;
		if(scale(this.height)<lines*scale(fontSize)) {
			changeHeight(lines*fontSize+10);
		}
		drawContent();
	}
	
	protected void changeWidth(int width) {
		this.width = width;
		if(width<30)
			this.width = 30;
		this.getAttribute("width").setValue(Integer.toString(this.width));
	}
	
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		
		if(scale(this.height)<lines*scale(fontSize)+10) {
			changeHeight(lines*fontSize+10);
		}
		drawContent();
	}
	
	protected void changeHeight(int height) {
		this.height = height;
		if(height<30)
			this.height = 30;
		this.getAttribute("height").setValue(Integer.toString(this.height));
	}
	
	private String makeHTMLString(String base) {
		String temp = base;
		temp = base.replaceAll("<", "&lt;");
		temp = temp.replaceAll(">", "&gt;");
		temp += "<br>";
		return temp;
	}
	
	@Override
	public String getContent() {
		String tag = this.elementTag.getTagname();
		String openTag = tag.substring(0, tag.indexOf("</"));
		String closeTag = tag.substring(tag.indexOf("</"));
		String childsContent = "";
		String styles = "style=\"";
		
		for(Attribute attr : attributes) {
			styles+=attr.getName()+":"+attr.getValue()+attr.getDefaultUnit()+"; ";
		}
		styles+="\"";
		for(PageElement element:childs) {
			childsContent+=element.getContent();
		}
		
		openTag = openTag.substring(0, openTag.indexOf(">"))+" "+styles+" >";
		
		return openTag+makeHTMLString(textData)+childsContent+closeTag;
	}

	public String getTextData() {
		return textData;
	}
	
	public void addTextData(String textData) {
		this.textData += textData;
		int len = img.getGraphics().getFontMetrics(new Font(fontFamily, Font.PLAIN, scale(fontSize))).stringWidth(textData);
		if(len>=lines*(scale(width)-scale(fontSize)/2)) {
			lines++;
			if(scale(this.height)<lines*scale(fontSize)+10)
				changeHeight(lines*fontSize+10);
		}
		drawContent();
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