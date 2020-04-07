package com.nm.wpc.editor.option;

import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.screen.InputPanel;
import com.nm.wpc.screen.MainScreen;

public class NewProjectOption extends Option{
	
	public NewProjectOption(MainScreen ms) {
		super(ms);
		this.optName = "New project";
	}
	
	@Override
	public void make(GUIObject source) {
		InputPanel ip = new InputPanel(source, source.getX()+source.getWidth(), source.getY());
		ip.setTITLE("Create new project:");
		ip.addGUIObject(new Button(ip.getX()+5, ip.getY()+50, ip.getW()-10, 20, this).setContainer(ip));
		ip.addGUIObject(new InputField("Test", ip.getX()+5, ip.getY()+80, ip.getW()-10, 50, 1).setContainer(ip));
		ip.drawContent();
		source.getContainer().drawPanel(ip);;
		ms.repaint();
	}
}

