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

package com.nm.wpc.editor.option;

import java.util.List;

import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.*;
import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.screen.Panel;
import com.nm.wpc.window.FormWindow;

/*
 * Class: com.nm.wpc.editor.option.CreateProjectOption
 * Superclass : com.nm.wpc.editor.option.Option
 * Used for: Submitting form window
 */

public class CreateProjectOption extends Option{
	private FormWindow fw;
	
	public CreateProjectOption(FormWindow fw) {
		this.fw = fw;
	}
	
	public CreateProjectOption(FormWindow fw,MainScreen ms) {
		super(ms);
		this.fw = fw;
	}
	
	@Override
	public void make(GUIObject source) {
		Panel p = fw.getPanel();

		ProjectManager pm = new ProjectManager();
		
		List<GUIObject> inputs = p.getObjects();
		String[] projectData = {((InputField)inputs.get(0)).getText(), ((InputField)inputs.get(1)).getText(), ((InputField)inputs.get(3)).getText()};
		
		if(!projectData[0].equals("") && !projectData[1].equals("") && !projectData[2].equals("")) {
			pm.createNewProject(projectData[0], projectData[1], projectData[2]);
			
			ms.changeContent(((InputField)inputs.get(0)).getText());
			fw.dispose();
		}
	}
}
