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

package com.nm.elems.elements;

import com.nm.elems.Attribute;
import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;

public class Anchor extends TextBox {
	String link;
	
	public Anchor(String tagname) {
		super(tagname);
		String addrs = tagname.substring(tagname.indexOf("href=\"")+6,tagname.lastIndexOf("\""));
		this.getAttribute("href").setValue(addrs);
		this.link = addrs;
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
		String temp = base;
		temp = base.replaceAll("<", "&lt;");
		temp = temp.replaceAll(">", "&gt;");
		temp += "<br>";
		return temp;
	}
	
}
