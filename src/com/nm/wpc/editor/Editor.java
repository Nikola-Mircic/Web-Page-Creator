/*
 * Class: com.nm.wpc.editor.Editor
 * Superclass :
 * Used for: allows you to change different pages or elements
 */
package com.nm.wpc.editor;

import com.nm.wpc.screen.*;

public class Editor extends Screen{
	private static final long serialVersionUID = 1L;
	
	protected WorkingScreen ws;
	
	public Editor(int x,int y,int width,int height,WorkingScreen ws) {
		super(x,y,width,height);
		this.setWs(ws);
	}
	
	public void update(int x,int y,int width,int height,WorkingScreen ws) {
		setX(x);
		setY(y);
		setW(width);
		setH(height);
		setWs(ws);
		drawContent(width, height);
	}
	
	public void callback(Screen destination) {
		
	}

	public WorkingScreen getWs() {
		return ws;
	}

	public void setWs(WorkingScreen ws) {
		this.ws = ws;
	}
}
