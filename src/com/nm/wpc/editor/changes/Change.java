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

import java.lang.reflect.Field;

import com.nm.wpc.screen.WorkingScreen;

public abstract class Change<T, V>{
	protected WorkingScreen ws;
	protected T changedObject;
	protected V data;
	protected V lastData;
	protected Field field;
	
	public Change(WorkingScreen ws,T changedObject,String field) {
		this.ws = ws;
		this.changedObject = changedObject;
		if(!field.equals("")) {
			try {
				this.field = changedObject.getClass().getField(field);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void changeData(V data, V lastData);
	
	protected abstract void undo();
	
	protected abstract void redo();
}