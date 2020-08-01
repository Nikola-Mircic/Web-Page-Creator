package com.nm.elems;

import com.nm.wpc.gui.*;

public class Attribute {
	private String name,value,defaultUnit;
	private GUIObject edit;
	
	public Attribute(String name,String value,String defaultUnit) {
		setName(name);
		setValue(value);
		setDefaultUnit(defaultUnit);
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

	public String getDefaultUnit() {
		return defaultUnit;
	}

	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}

	public GUIObject getEdit() {
		return edit;
	}

	public void setEdit(GUIObject edit) {
		this.edit = edit;
	}
}
