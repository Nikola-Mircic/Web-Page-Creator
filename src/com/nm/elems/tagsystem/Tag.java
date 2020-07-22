package com.nm.elems.tagsystem;

import java.util.ArrayList;
import java.util.List;

import com.nm.elems.*;
import com.nm.wpc.filesystem.FileManager;

public enum Tag {
	BOX("<div></div>", 8191),
	TEXT_BOX("<p></p>", 32767),
	HEADING_1("<h1></h1>", 32767),
	HEADING_2("<h2></h2>", 32767),
	HEADING_3("<h3></h3>", 32767),
	HEADING_4("<h4></h4>", 32767),
	HEADING_5("<h5></h5>", 32767),
	HEADING_6("<h6></h6>", 32767),
	BODY("<body></body>", 8191);
	
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
			if((bitmask & (1<<d))>>d == 1)
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
