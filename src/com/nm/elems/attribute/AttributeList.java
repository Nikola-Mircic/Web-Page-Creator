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

import java.util.ArrayList;
import java.util.List;

import com.nm.wpc.gui.InputField;

public class AttributeList {
	public List<Attribute> attributes;
	
	public AttributeList() {
		this.attributes = new ArrayList<>();
	}
	
	public Attribute get(int idx) {
		return this.attributes.get(idx);
	}
	
	public int indexOf(Attribute attr) {
		int idx = -1;
		for(Attribute attribute : attributes) {
			idx++;
			if(attr.getName().equals(attribute.getName())){
				return idx;
			}
		}
		return -1;
	}
	
	public Attribute getAttribute(String attrName) {
		for(Attribute temp : attributes) {
			if(temp.getName().equals(attrName))
				return temp;
		}
		return null;
	}
	
	public String getAttributeValue(int index) {
		if(index>=attributes.size() || index<0)
			return "";
		return attributes.get(index).getValue();
	}
	
	public String getAttributeValue(String attrName) {
		for(Attribute temp : attributes){
			if(temp.getName().equals(attrName)) {
				return temp.getValue();
			}	
		}
		return "";
	}
	
	public void setAttributeValue(int idx,InputField field) {}
	public void setAttributeValue(int idx,String newValue) {}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
