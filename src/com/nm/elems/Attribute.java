package com.nm.elems;

import com.nm.wpc.gui.*;

public class Attribute {
	private String name,value;
	private GUIObject edit;
	
	public Attribute(String name,String value) {
		setName(name);
		setValue(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GUIObject getEdit() {
		return edit;
	}

	public void setEdit(GUIObject edit) {
		this.edit = edit;
	}
}
