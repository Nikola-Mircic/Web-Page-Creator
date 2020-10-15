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

import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.gui.InputField;

public class AttributeList {
	private List<Attribute> attributes;
	
	public AttributeList() {
		this.attributes = new ArrayList<>();
	}
	
	public AttributeList(Tag elementTag) {
		this.attributes = generateAttributes(elementTag.getBitmask());
		generateDefaultAttributes(elementTag);
	}
	
	private List<Attribute> generateAttributes(int bitmask){
		FileManager files = new FileManager();//for getting all attributes from attribute.dat file
		Attribute[] attrList = files.getAttributes();//storing attributes to list
		List<Attribute> temp = new ArrayList<Attribute>();//return data
		int d;//for storing position of the byte
		for(int i=0;i<attrList.length;++i) {
			d = attrList.length-i-1;
			if((bitmask & (1<<d))>>d == 1)
				temp.add(attrList[i]);
		}
		
		return temp;
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
	
	public void setAttributeValue(int index,InputField field) {
		String newValue = field.getText();
		attributes.get(index).setValue(newValue);
		Attribute temp = attributes.get(index);
		
		String color[];
		switch(temp.getName()) {
			case "position":
				if(!newValue.equals("absolute")) {
					field.setText("absolute");
				}
				break;
			case "background-color":
				color = newValue.substring(newValue.indexOf('(')+1,newValue.indexOf(')')).split(",");
				for(int i=0;i<4;i++) {
					if(color[i].equals(""))
						color[i] = "0";
					else if(Float.parseFloat(color[i])<0)
						color[i]="0";
					else {
						if(Float.parseFloat(color[i])>255 && i<3)
							color[i]="255";
						else if(Float.parseFloat(color[i])>1.0 && i==3)
							color[i]="1.0";
					}
				}
				newValue = newValue.substring(0,newValue.indexOf('(')+1)+String.join(",", color)+")";
				attributes.get(index).setValue(newValue);
				field.setText(newValue);
				break;
			case "color":
				color = newValue.substring(newValue.indexOf('(')+1,newValue.indexOf(')')).split(",");
				for(int i=0;i<4;i++) {
					if(color[i].equals(""))
						color[i] = "0";
					else if(Float.parseFloat(color[i])<0)
						color[i]="0";
					else {
						if(Float.parseFloat(color[i])>255 && i<3)
							color[i]="255";
						else if(Float.parseFloat(color[i])>1.0 && i==3)
							color[i]="1.0";
					}
				}
				newValue = newValue.substring(0,newValue.indexOf('(')+1)+String.join(",", color)+")";
				attributes.get(index).setValue(newValue);
				field.setText(newValue);
				break;
			default:
				setAttributeValue(index, newValue);
				break;
		}
	}
	
	public void setAttributeValue(int index,String newValue) {
		attributes.get(index).setValue(newValue);
		Attribute temp = attributes.get(index);
		switch(temp.getName()) {
			case "margin-top":
				switch (getAttribute("position").getValue()) {
					case "absolute":
						if(newValue.equals("")) {
							attributes.get(index).setValue("0");
							temp.setValue("0");
						}
						break;
					default:
						break;
				}
				break;
			case "margin-left":
				switch (getAttribute("position").getValue()) {
				case "absolute":
					if(newValue.equals("")) {
						attributes.get(index).setValue("0");
						temp.setValue("0");
					}
					break;
				default:
					break;
				}
				break;
			case "background-color":
				String color[] = newValue.substring(newValue.indexOf('(')+1,newValue.indexOf(')')).split(",");
				for(int i=0;i<3;i++) {
					if(Integer.parseInt(color[i])<0)
						color[i]="0";
				}
				if(Float.parseFloat(color[3]) < 0)
					color[3] = "0.0";
				newValue = newValue.substring(0,newValue.indexOf('(')+1)+String.join(",", color)+")";
				attributes.get(index).setValue(newValue);
			default:
				break;
		}
	}
	
	private void generateDefaultAttributes(Tag tag) {
		switch (tag) {
			case BOX:
				getAttribute("background-color").setValue("rgba(150,150,150,1.0)");
				getAttribute("width").setValue("200");
				getAttribute("height").setValue("100");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-left").setValue("0");
			break;
			case TEXT_BOX:
				getAttribute("font-size").setValue("20");
				getAttribute("font-family").setValue("Serif");
				getAttribute("color").setValue("rgba(0,0,0,1.0)");
				getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
				getAttribute("width").setValue("250");
				getAttribute("height").setValue("30");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-left").setValue("0");
				break;
			case BODY:
				getAttribute("background-color").setValue("rgba(136,225,247,1.0)");
				getAttribute("width").setValue("1000");
				getAttribute("height").setValue("700");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-left").setValue("0");
				break;
			case ANCHOR:
				getAttribute("font-size").setValue("20");
				getAttribute("font-family").setValue("Serif");
				getAttribute("color").setValue("rgba(10,10,200,1.0)");
				getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
				getAttribute("width").setValue("250");
				getAttribute("height").setValue("30");
				getAttribute("margin-top").setValue("0");
				getAttribute("margin-left").setValue("0");
				break;
			default:
				String tagString = tag.toString();
				if(tagString.substring(0,7).equals("HEADING")) {
					int headingType = Integer.parseInt(""+tagString.charAt(8));
					getAttribute("font-size").setValue(Integer.toString(25+(6-headingType)*10));
					getAttribute("font-family").setValue("Times New Roman");
					getAttribute("color").setValue("rgba(255,255,255,1.0)");
					getAttribute("background-color").setValue("rgba(0,0,0,0.0)");
					getAttribute("width").setValue(Integer.toString(300+(6-headingType)*25));
					getAttribute("height").setValue(Integer.toString(35+(6-headingType)*10));
					getAttribute("margin-top").setValue("0");
					getAttribute("margin-left").setValue("0");
				}
				break;
		}
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
