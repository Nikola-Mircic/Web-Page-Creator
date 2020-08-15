package com.nm.elems.loader;

import java.util.Stack;

import com.nm.elems.Page;
import com.nm.elems.PageElement;
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
		//p.setPageBody(pageBody);
		String pageContent = "<!DOCTYPE html><html>"+pageHead+pageBody+"</html>";
		p.setPageContent(pageContent);
		
		return p;
	}
	
	public Page convertToPage(String source) {
		Page p = new Page();
		
		//Page heading
		int l = source.indexOf("<head>");
		int r = source.indexOf("<body>");
		if(l==-1 || r==-1)
			return createBlankPage();
		p.setPageHead(source.substring(l,r));
		
		//Page elements
		source = source.substring(r+6,source.indexOf("</body>"));
		Stack<String> s = new Stack<>();
		Stack<PageElement> elems = new Stack<>();
		int idx = -1;
		for(int i=0;i<source.length();++i) {
			idx = source.indexOf(' ');
			if(isOpenTag(source.substring(i,idx)+">")) {
				s.push(source.substring(i,idx)+">");
				
			}else if(!s.empty()) {
				
			}
		}
		
		return createBlankPage();
	}
	
	private boolean isOpenTag(String testTag) {
		for(Tag tag:Tag.values()) {
			if(tag.getTagname().substring(0, (tag.getTagname().length()-1)/2).equals(testTag))
				return true;
		}
		return false;
	}

}
