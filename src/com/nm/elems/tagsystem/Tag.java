package com.nm.elems.tagsystem;

import java.util.ArrayList;
import java.util.List;

import com.nm.elems.*;
import com.nm.wpc.filesystem.FileManager;

public enum Tag {
	BOX("<div></div>", 8191),
	TEXT_BOX("<p></p>", 8191),
	HEADING_1("<h1></h1>", 8191),
	HEADING_2("<h2></h2>", 8191),
	HEADING_3("<h3></h3>", 8191),
	HEADING_4("<h4></h4>", 8191),
	HEADING_5("<h5></h5>", 8191),
	HEADING_6("<h6></h6>", 8191);
	
	private final List<Attribute> attributes;
	
	//attributeBitmaks is bitmask which represent attributes which can be added to an element
	private Tag(String tag,int attributeBitmask) {
		attributes = generateAttributes(attributeBitmask);
	}
	
	private List<Attribute> generateAttributes(int bitmask){
		FileManager files = new FileManager();
		Attribute[] attrList = files.getAttributes();
		System.out.println(attrList[0].getName());
		List<Attribute> temp = new ArrayList<Attribute>();
		for(int i=0;i<attrList.length;++i) {
			if((bitmask & (1<<i))>>i == 1)
				temp.add(attrList[i]);
		}
		
		return temp;
	}
	
	public List<Attribute> getAttributes(){
		return this.attributes;
	}
}
