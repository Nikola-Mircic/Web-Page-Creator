package com.nm.wpc.editor.option;

import java.awt.Dimension;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.InputPanel;
import com.nm.wpc.screen.MainScreen;

public class NewProjectOption extends Option{
	
	public NewProjectOption(MainScreen ms) {
		super(ms);
		this.optName = "New project";
	}
	
	@Override
	public void make(GUIObject source) {
		InputPanel ip = createPanel(source,ms.createPanelDimension());
		ip.setTITLE("Create new project:");
		
		ms.drawPanel(ip);
	}
	
	private InputPanel createPanel(GUIObject source,Dimension dim) {
		return new InputPanel(source,dim.width, dim.height);
	}
}

