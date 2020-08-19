package com.nm.wpc.editor.option;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.window.FormWindow;

public class OpenProjectOption extends Option {

	public OpenProjectOption() {
		this.optName = "Open project";
	}

	public OpenProjectOption(MainScreen ms) {
		super(ms);
		this.optName = "Open project";
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
		FormWindow fw = new FormWindow("Select Project: ", 200, recentProjects.size()*60+200);
		for(int i=0;i<recentProjects.size();++i) {
			Map<String,String> project = recentProjects.get(i);
			btn = new Button(project.get("name"), 15, i*50, 200, 50, new Option(ms) {
				@Override
				public void make(GUIObject source) {
					ms.changeContent(project.get("name"));
					fw.dispose();
				}
			});
			fw.addGUIObject(btn);
		}
		
		InputField projectLocation = new InputField("Project settings file:", 15, recentProjects.size()*50+10, 200, 100, 1);
		Button browse = new Button("Browse", 155, recentProjects.size()*50+20, 50, 30, new SelectFileOption()).setGuiObject(projectLocation);
		
		fw.addGUIObject(projectLocation);
		fw.addGUIObject(browse);
		
		fw.addGUIObject(new Button("Submit", 70, 300 , 100, 40, new Option(ms) {
			@Override
			public void make(GUIObject source) {
				if(projectLocation.getText().equals("") || projectLocation.getText() == null)
					return;
				FileManager fm = new FileManager();
				String projectData;
				try {
					projectData = fm.readFile(new File(projectLocation.getText()));
				} catch (IOException e) {
					fw.dispose();
					return;
				}
				pm.openProjectData(projectData.split(";"));
				ms.changeContent(projectData.substring(0,projectData.indexOf(";")));
				fw.dispose();
			}
		}));
		
		fw.createWindow();
	}
}
