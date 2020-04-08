package com.nm.wpc.editor.option;

import java.io.File;

import javax.swing.JFileChooser;

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
		JFileChooser test = new JFileChooser();
		test.setCurrentDirectory(new File("."));
		test.setDialogTitle("Choose a directory");
		test.setFileSelectionMode(this.mode);
	    test.setAcceptAllFileFilterUsed(false);

		int val = test.showOpenDialog(ms);
		if(val == JFileChooser.APPROVE_OPTION) {
			((InputField)((Button)source).getGuiObject()).setText(test.getSelectedFile().getAbsolutePath());
		}
	}
}
