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
