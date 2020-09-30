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

/*
 * Class: com.nm.wpc.editor.option.ContinueRecentOption
 * Superclass : com.nm.wpc.editor.option.Option
 * Used for: find list of recent projects and load working screen for selected project
 */

package com.nm.wpc.editor.option;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.InputPanel;
import com.nm.wpc.screen.MainScreen;

public class ContinueRecentOption extends Option{
	
	public ContinueRecentOption() {
		this.optName = "Continue recent";
	}
	
	public ContinueRecentOption(MainScreen ms) {
		super(ms);
		this.optName = "Continue recent";
	}

	@Override
	public void make(GUIObject source) {
		ProjectManager pm = new ProjectManager();
		List<Map<String, String>> recentProjects;
		try {
			recentProjects = pm.getRecentProject();
		} catch (ParseException e) {
			return;
		}
		Button btn;
		InputPanel ip = new InputPanel(source,source.getX()+source.getWidth(),source.getY());
		
		for(int i=0;i<recentProjects.size();++i) {
			Map<String,String> project = recentProjects.get(i);
			btn = new Button(project.get("name"), source.getX()+source.getWidth(), source.getY()+i*50, 200, 50, new Option(ms) {
				@Override
				public void make(GUIObject source) {
					ms.changeContent(project.get("name"));
				}
			});
			ip.addGUIObject(btn);
		}
		ip.drawContent();
		ms.drawPanel(ip);
	}
}
