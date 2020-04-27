package com.nm.elems.loader;

import com.nm.elems.Page;

public class PageLoader {
	public PageLoader() {}
	
	public PageLoader(String path) {
		
	}
	
	public Page createBlankPage() {
		Page p = new Page();
		createBlankPage(p);
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
		String pageBody = "<body>"+p.getElementsContent()+"</body>";
		p.setPageBody(pageBody);
		String pageContent = "<!DOCTYPE html><html>"+pageHead+pageBody+"</html>";
		p.setPageContent(pageContent);
	}
}
