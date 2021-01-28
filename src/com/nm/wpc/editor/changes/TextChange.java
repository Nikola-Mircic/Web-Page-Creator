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

import com.nm.elems.elements.TextBox;
import com.nm.wpc.screen.WorkingScreen;

public class TextChange extends Change<TextBox, String> {

	public TextChange(WorkingScreen ws, TextBox changedObject, String field) {
		super(ws, changedObject, field);
	}
	
	public static TextChange makeChange(WorkingScreen ws, TextBox changedObject, String data, String lastData) {
		TextChange temp = new TextChange(ws, changedObject, "");
		temp.changeData(data, lastData);
		return temp;
	}

	@Override
	protected void changeData(String data, String lastData) {
		this.data = data;
		this.lastData = lastData;
		changedObject.setLastSavedData(data);
		changedObject.isChanged = false;
		changedObject.drawContent();
	}

	@Override
	protected void undo() {
		changedObject.setTextData(lastData);
		changedObject.setLastSavedData(lastData);
		changedObject.drawContent();
	}

	@Override
	protected void redo() {
		changedObject.setTextData(data);
		changedObject.setLastSavedData(data);
		changedObject.drawContent();
	}

}
