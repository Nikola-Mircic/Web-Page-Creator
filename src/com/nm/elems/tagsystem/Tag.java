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

package com.nm.elems.tagsystem;

import java.util.ArrayList;
import java.util.List;

import com.nm.elems.attribute.Attribute;
import com.nm.wpc.filesystem.FileManager;

public enum Tag {
	BOX("<div></div>", 63),
	TEXT_BOX("<p></p>", 511),
	HEADING_1("<h1></h1>", 511),
	HEADING_2("<h2></h2>", 511),
	HEADING_3("<h3></h3>", 511),
	HEADING_4("<h4></h4>", 511),
	HEADING_5("<h5></h5>", 511),
	HEADING_6("<h6></h6>", 511),
	ANCHOR("<a></a>",1023),
	BODY("<body></body>", 63);
	
	private final List<Attribute> attributes;
	private final String tagname;
	private final int bitmask;
	
	//attributeBitmaks is bitmask which represent attributes which can be added to an element
	private Tag(String tag,int attributeBitmask) {
		this.tagname = tag;
		this.bitmask = attributeBitmask;
		this.attributes = generateAttributes(attributeBitmask);
	}
	
	//Generate list of attributes from given bitmask
	private List<Attribute> generateAttributes(int bitmask){
		FileManager files = new FileManager();//for getting all attributes from attribute.dat file
		Attribute[] attrList = files.getAttributes();//storing attributes to list
		List<Attribute> temp = new ArrayList<Attribute>();//return data
		int d;//for storing position of the byte
		for(int i=0;i<attrList.length;++i) {
			d = attrList.length-i-1;
			if((bitmask & (1<<d))>= 1)
				temp.add(attrList[i]);
		}
		
		return temp;
	}
	
	public List<Attribute> getAttributes(){
		return this.attributes;
	}

	public String getTagname() {
		return tagname;
	}
	
	public int getBitmask() {
		return bitmask;
	}
}
