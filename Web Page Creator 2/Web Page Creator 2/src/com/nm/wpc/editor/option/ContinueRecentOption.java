package com.nm.wpc.editor.option;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.MainScreen;

public class ContinueRecentOption extends Option{
	
	public ContinueRecentOption() {
		this.optName = "Continue recent";
	}
	
	public ContinueRecentOption(MainScreen ms) {
		super(ms);
		this.optName = "Continue recent";
	}

	@Override
	public void make(GUIObject source) {
		ms.changeContent();
	}
}
