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

package com.nm.elems.attribute;

import com.nm.wpc.gui.InputField;

public class PageAttributeList extends AttributeList {

	public PageAttributeList() {
		super();
		
		this.attributes.add(new Attribute("title", "WPC page", ""));
		this.attributes.add(new Attribute("encoding", "utf-8", ""));
	}
	
	@Override
	public void setAttributeValue(int index,InputField field) {
		String newValue = field.getText();
		attributes.get(index).setValue(newValue);
	}
}
