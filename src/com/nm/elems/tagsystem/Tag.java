package com.nm.elems.tagsystem;

import java.util.ArrayList;
import java.util.List;

import com.nm.elems.*;
import com.nm.wpc.filesystem.FileManager;

public enum Tag {
	BOX("<div></div>", 0),
	TEXT_BOX("<p></p>", 0),
	HEADING_1("<h1></h1>", 0),
	HEADING_2("<h2></h2>", 0),
	HEADING_3("<h3></h3>", 0),
	HEADING_4("<h4></h4>", 0),
	HEADING_5("<h5></h5>", 0),
	HEADING_6("<h6></h6>", 0);
	
	private final List<Attribute> attributes;
	
	//attributeBitmaks is bitmask which represent attributes which can be added to an element
	private Tag(String tag,int attrinuteBitmask) {
		attributes = generateAttributes(attrinuteBitmask);
	}
	
	private List<Attribute> generateAttributes(int bitmask){
		FileManager files = new FileManager();
		Attribute[] attrList = files.getAttributes();
		
		List<Attribute> temp = new ArrayList<Attribute>();
		for(int i=0;i<attrList.length;++i) {
			if((bitmask & (1<<i)) == 1)
				temp.add(attrList[i]);
		}
		
		return temp;
	}
	
	public List<Attribute> getAttributes(){
		return this.attributes;
	}
}
