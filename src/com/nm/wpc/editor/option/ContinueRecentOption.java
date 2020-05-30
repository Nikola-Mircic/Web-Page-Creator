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
		Map<String,String> project;
		for(int i=0;i<recentProjects.size();++i) {
			project = recentProjects.get(i);
			btn = new Button(project.get("name"), source.getX()+source.getWidth(), source.getY()+i*50, 200, 50, new Option(ms) {
				@Override
				public void make(GUIObject source) {
					ms.changeContent();
				}
			});
			ip.addGUIObject(btn);
		}
		ip.drawContent();
		ms.drawPanel(ip);
	}
}
