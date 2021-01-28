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

package com.nm.wpc.gui.option;

import com.nm.wpc.screen.MainScreen;
import com.nm.elems.Page;
import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.*;

public class SaveProjectOption extends Option {
	private String projectName;
	private Page page;
	
	public SaveProjectOption() {
		
	}

	public SaveProjectOption(MainScreen ms) {
		super(ms);
		setOptName("Save Project");
	}
	
	public SaveProjectOption(MainScreen ms,Page page,String projectName) {
		super(ms);
		this.projectName = projectName;
		this.page = page;
		setOptName("Save Project");
	}
	
	@Override
	public void make(GUIObject source) {
		ProjectManager pm = new ProjectManager();
		pm.convertPageToHTML(page, projectName);
	}

}
