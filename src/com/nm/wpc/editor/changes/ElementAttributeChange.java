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

package com.nm.wpc.editor.changes;

import com.nm.elems.PageElement;
import com.nm.elems.attribute.ElementAttributeList;
import com.nm.wpc.screen.WorkingScreen;

public class ElementAttributeChange extends Change<PageElement, String> {
	private int index;
	
	private ElementAttributeChange(WorkingScreen ws,PageElement changedObject,int index) {
		super(ws, changedObject, "attributes");
		this.index = index;
	}
	
	public static ElementAttributeChange makeChange(WorkingScreen ws,PageElement changedObject, int index, String data, String lastData) {
		ElementAttributeChange temp = new ElementAttributeChange(ws, changedObject, index);
		temp.changeData(data, lastData);
		return temp;
	}
	
	@Override
	protected void changeData(String data, String lastData) {
		this.data = data;
		this.lastData = lastData;
	}

	@Override
	public void undo() {
		if(this.field==null)
			return;
		try {
			ElementAttributeList list = (ElementAttributeList) field.get(changedObject);
			list.setAttributeValue(index, lastData);
			
			ws.createEditor(changedObject);
			changedObject.drawContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void redo() {
		if(this.field==null)
			return;
		try {
			ElementAttributeList list = (ElementAttributeList) field.get(changedObject);
			list.setAttributeValue(index, data);
			
			ws.createEditor(changedObject);
			changedObject.drawContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
