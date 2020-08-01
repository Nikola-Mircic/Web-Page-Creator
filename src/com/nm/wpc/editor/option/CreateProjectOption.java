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
		System.out.println("Submiting...");
		ProjectManager pm = new ProjectManager();
		List<GUIObject> inputs = p.getObjects();
		pm.createNewProject(((InputField)inputs.get(0)).getText(), ((InputField)inputs.get(1)).getText(), ((InputField)inputs.get(3)).getText());
		ms.changeContent(((InputField)inputs.get(0)).getText());
		fw.dispose();
	}
}
