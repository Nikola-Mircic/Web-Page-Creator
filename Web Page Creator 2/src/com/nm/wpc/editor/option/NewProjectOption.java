package com.nm.wpc.editor.option;

import javax.swing.JFileChooser;

import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
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
		FormWindow fw = new FormWindow("Create new project:",300,500);
		
		InputField input;
		Button btn;
		input = new InputField("Project name:", 20, 20, 260,80,1);
		fw.addInputField(input);
		input = new InputField("Project location:", 20, 110, 260,80,1);
		fw.addInputField(input);
		btn = new Button(225, 115, 30, 30, new SelectFileOption(JFileChooser.DIRECTORIES_ONLY));
		btn.setGuiObject(input);
		fw.addButton(btn);
		input = new InputField("Entry point:", 20, 200, 260,80,1);
		fw.addInputField(input);
		
		btn = new Button("Submit", 110, 300 , 80, 40, new Option());
		fw.addButton(btn);
		
		fw.createWindow();
	}
}

