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

package com.nm.elems.loader;

import java.util.Stack;

import com.nm.elems.Page;
import com.nm.elems.PageElement;
import com.nm.elems.elements.Anchor;
import com.nm.elems.elements.TextBox;
import com.nm.elems.tagsystem.Tag;

/*
 * Class: com.nm.elems.loader.PageLoader
 * Superclass :
 * Used for: Create new blank page or read data from existing page.
 */

public class PageLoader {
	public PageLoader() {}
	
	public Page createBlankPage() {
		Page p = new Page();
		p = makeBlankPage(p);
		return p;
	}
	
	public void createBlankPage(Page p) {
		p.setTITLE("WPC Page");
		String pageHead = "<head>"+
				   		  " <meta charset=\"UTF-8\">"+
				   		  " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
				   		  " <title>"+p.getTITLE()+"</title>"+
				   		  "</head>";
		p.setPageHead(pageHead);
		String pageBody = "<body></body>";
		//p.setPageBody(pageBody);
		String pageContent = "<!DOCTYPE html><html>"+pageHead+pageBody+"</html>";
		p.setPageContent(pageContent);
	}
	
	public Page makeBlankPage(Page p) {
		p.setTITLE("WPC Page");
		String pageHead = "<head>"+
				   		  " <meta charset=\"UTF-8\">"+
				   		  " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
				   		  " <title>"+p.getTITLE()+"</title>"+
				   		  "</head>";
		p.setPageHead(pageHead);
		String pageBody = "<body></body>";
		String pageContent = "<!DOCTYPE html><html>"+pageHead+pageBody+"</html>";
		p.setPageContent(pageContent);
		
		return p;
	}
	
	public Page convertToPage(String source) {
		Page p = new Page();
		
		//Page heading
		int l = source.indexOf("<head>");
		int r = source.indexOf("<body");
		if(l==-1 || r==-1)
			return createBlankPage();
		
		p.setPageHead(source.substring(l,r));
		//Page elements
		source = source.substring(r,source.indexOf("</body>"));
		source = source.substring(source.indexOf('>')+1);
		
		Stack<String> s = new Stack<>();
		Stack<PageElement> elems = new Stack<>();
		String tag;
		int start = 0;
		tag=findNextTag(start, source);

		while(!tag.equals("")) {
			if(tag.indexOf(' ')!=-1) {
				if(isOpenTag(tag.substring(0,tag.indexOf(' '))+">")) {
					s.push(getCloseTag(tag));
					PageElement temp = getElement(getFullTag(tag));
					if(!elems.empty()) {
						elems.peek().addElement(temp);
						elems.push(temp);
					}else {
						elems.push(temp);
						p.addElement(temp);
					}
					start += tag.length();
				}
			}else if(!s.empty()) {
				if(tag.equals("<br>")) {
					String temp = source.substring(start);
					String textData = temp.substring(0,temp.indexOf(tag));
					textData = textData.replaceAll("&lt;", "<");
					textData = textData.replaceAll("&gt;", ">");
					((TextBox)elems.peek()).addTextData(textData);
					start+=temp.indexOf(tag)+4;
				}else if(isCloseTag(tag) && s.peek().equals(tag)) {
					s.pop();
					elems.pop();
					start += tag.length();				}
			}
			tag=findNextTag(start, source);
		}
		
		return p;
	}
	
	private String findNextTag(int start,String base) {
		String tempBase = base.substring(start);
		int idx1 = tempBase.indexOf('<'),
			idx2 = tempBase.indexOf('>');
		if(idx1<idx2 && idx1!=-1 && idx2!=-1) {
			return tempBase.substring(idx1, idx2+1);
		}
		return "";
	}
	
	private String getFullTag(String openTag) {
		if(openTag=="")
			return openTag;
		return openTag+getCloseTag(openTag);
	}
	
	private String getCloseTag(String tag) {
		return "</"+tag.substring(1,tag.indexOf(' '))+">";
	}
	
	private boolean isOpenTag(String testTag) {
		for(Tag tag:Tag.values()) {
			if(tag.getTagname().substring(0, (tag.getTagname().length()-1)/2).equals(testTag))
				return true;
		}
		return false;
	}
	
	private boolean isCloseTag(String testTag) {
		return testTag.indexOf('/')!=-1;
	}
	
	private PageElement getElement(String tagname) {
		Tag tag = generateTag(tagname);
		switch (tag) {
		case TEXT_BOX:
			return new TextBox(tagname);
		case ANCHOR:
			return new Anchor(tagname);
		default:
			if(tag == Tag.HEADING_1 || tag == Tag.HEADING_2 || tag == Tag.HEADING_3 ||
			   tag == Tag.HEADING_4 || tag == Tag.HEADING_5 || tag == Tag.HEADING_6) {
				return new TextBox(tagname);
			}
			return new PageElement(tagname);
		}
	}
	
	private Tag generateTag(String tagname) {
		if(tagname.indexOf(' ')!=-1) {
			tagname = tagname.substring(0,tagname.indexOf(" "))+">"+tagname.substring(tagname.lastIndexOf("<"));
		}
		Tag temp = null;
		Tag[] tags = Tag.values();
		for(Tag tag:tags) {
			if(tag.getTagname().equals(tagname)) {
				temp = tag;
				break;
			}
		}
		
		return temp;
	}
}
