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
		try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
		}
		JFileChooser choose = new JFileChooser();
		choose.setCurrentDirectory(new File("."));
		choose.setDialogTitle("Choose a directory");
		choose.setFileSelectionMode(this.mode);
		choose.setAcceptAllFileFilterUsed(false);

		int val = choose.showOpenDialog(ms);
		if(val == JFileChooser.APPROVE_OPTION) {
			((InputField)((Button)source).getGuiObject()).setText(choose.getSelectedFile().getAbsolutePath());
		}
	}
}
