package com.nm.elems;

import java.util.List;

import com.nm.wpc.editor.option.*;

/*
 * Class: com.nm.elems.PageElement
 * Superclass :
 * Used for: organizing data of single element on a page
 */

public class PageElement {
	private List<Option> opts;
	
	public PageElement() {
		
	}

	public List<Option> getOpts() {
		return opts;
	}

	public void setOpts(List<Option> opts) {
		this.opts = opts;
	}
}
