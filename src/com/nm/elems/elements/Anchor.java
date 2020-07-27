package com.nm.elems.elements;

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

}
