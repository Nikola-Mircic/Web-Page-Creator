package com.nm.wpc.editor.option;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.window.FormWindow;

public class NewProjectOption extends Option{
	
	public NewProjectOption() {
		this.optName = "New project";
	}
	
	public NewProjectOption(MainScreen ms) {
		super(ms);
		this.optName = "New project";
	}
	
	@Override
	public void make(GUIObject source) {
		FormWindow fw = new FormWindow("Create new project:");
		fw.createWindow();
	}
}

