package com.nm.wpc.editor.option;

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
