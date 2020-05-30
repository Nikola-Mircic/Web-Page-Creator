package com.nm.wpc.editor.option;

import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.MainScreen;

/*
 * Class: com.nm.wpc.editor.option.Option
 * Superclass : 
 * Used for: Defining options
 */

public class Option {
  	protected String optName;
  	protected MainScreen ms;
  	
  	public Option() {
  	}
  	
  	public Option(MainScreen ms) {
  		this.ms = ms;
  	}
  	
  	public void make(GUIObject source) {}
	public void redo() {}
  	public void undo() {}
  	
  	public String getOptName() {
		return this.optName;
	}
  	
	public void setOptName(String optName) {
		this.optName = optName;
	}
} 
