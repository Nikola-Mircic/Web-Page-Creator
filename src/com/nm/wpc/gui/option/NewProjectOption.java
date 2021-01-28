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

import javax.swing.JFileChooser;

import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.window.FormWindow;

/*
 * Class: com.nm.wpc.editor.option.CreateProjectOption
 * Superclass : com.nm.wpc.editor.option.Option
 * Used for: Making window for creating new empty project with a single blank page
 */

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
		btn = new Button("Browse",185, 115, 75, 30, new SelectFileOption(JFileChooser.DIRECTORIES_ONLY));
		btn.setGuiObject(input);
		fw.addButton(btn);
		input = new InputField("Entry point:", 20, 200, 260,80,1);
		fw.addInputField(input);
		
		btn = new Button("Submit", 110, 300 , 80, 40, new CreateProjectOption(fw,this.ms));
		fw.addButton(btn);
		
		fw.createWindow();
	}
}

