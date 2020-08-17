package com.nm.elems.elements;

import com.nm.elems.Attribute;
import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;

public class Anchor extends TextBox {
	String link;
	
	public Anchor(String tagname) {
		super(tagname);
		this.link = getAttributeValue("href");
	}

	public Anchor(Tag tag) {
		super(tag);
		this.link = getAttributeValue("href");
	}
	
	@Override
	public String getContent() {
		String tag = this.elementTag.getTagname();
		String openTag = tag.substring(0, tag.indexOf("</"));
		String closeTag = tag.substring(tag.indexOf("</"));
		String childsContent = "";
		String styles = "style=\"";
		
		for(Attribute attr : attributes) {
			if(!attr.getName().equals("href"))
				styles+=attr.getName()+":"+attr.getValue()+attr.getDefaultUnit()+"; ";
		}
		styles+="\"";
		
		styles+=" href=\""+getAttributeValue("href")+"\"";
		
		for(PageElement element:childs) {
			childsContent+=element.getContent();
		}
		
		openTag = openTag.substring(0, openTag.indexOf(">"))+" "+styles+" >";
		
		return openTag+makeHTMLString(textData)+childsContent+closeTag;
	}
	
	private String makeHTMLString(String base) {
		String temp = "",temp2;
		int idx = 0;
		for(int i=0;i<lines;++i) {
			ptLenght = findptLenght(img.getGraphics(), idx);
			temp2=base.substring(idx,idx+ptLenght);
			temp2 = temp2.replaceAll("<", "&lt;");
			temp2 = temp2.replaceAll(">", "&gt;");
			temp2+="<br>";
			temp+=temp2;
			idx+=ptLenght;
		}
		return temp;
	}
	
}
