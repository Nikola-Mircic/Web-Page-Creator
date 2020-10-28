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
 * Class: com.nm.wpc.editor.option.SelectFileOption
 * Superclass : com.nm.wpc.editor.option.Option;
 * Used for: Finding files and directories in filesystem
 */

package com.nm.wpc.editor.option;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.nm.wpc.gui.*;


public class SelectFileOption extends Option{
	private int mode;
	
	public SelectFileOption() {
		this.optName = "🔍";
		this.mode = JFileChooser.FILES_ONLY;
	}
	
	public SelectFileOption(int mode) {
		this.optName = "🔍";
		this.mode = mode;
	}
	
	@Override
	public void make(GUIObject source) {
		final int MODE = this.mode;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} 
				catch (Exception e) {
				}
				JFileChooser choose = new JFileChooser();
				choose.setCurrentDirectory(new File("."));
				choose.setDialogTitle("Choose a directory");
				choose.setFileSelectionMode(MODE);
				choose.setAcceptAllFileFilterUsed(false);

				int val = choose.showOpenDialog(ms);
				if(val == JFileChooser.APPROVE_OPTION) {
					((InputField)((Button)source).getGuiObject()).setText(choose.getSelectedFile().getAbsolutePath());
				}
				
			}
		});
		
		t.start();
	}
}
