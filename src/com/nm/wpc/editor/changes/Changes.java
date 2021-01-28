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

import java.util.ArrayList;
import java.util.List;

public class Changes {
	private static List<Change> history = new ArrayList<>();
	private static int lastChange = -1;
	
	
	public static void addChange(Change c) {
		if(lastChange<history.size()-1) {
			while(history.size()!=lastChange+1) {
				history.remove(lastChange+1);
			}
		}
		if(Changes.history.size()>=10) {
			Changes.history.remove(0);
			lastChange = 9;
		}else {
			lastChange++;
		}
		Changes.history.add(c);
	}
	
	public static void undoLastChange() {
		if(lastChange>=0) {
			history.get(lastChange).undo();
			lastChange--;
		}
	}
	
	public static void redoLastChange() {
		if(history.size()>0 && lastChange<history.size()-1) {
			history.get(lastChange+1).redo();
			lastChange++;
		}
	}
	
	public static void clearHistory() {
		history = new ArrayList<>();
		lastChange = -1;
	}
}
