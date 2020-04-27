package com.nm.wpc.editor.option;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.window.FormWindow;

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
		this.fw.submit();
	}
}
