package com.nm.wpc.input;

import java.awt.event.MouseEvent;

import com.nm.wpc.screen.Screen;

public class InputHandler {
	private Screen screen;
	private InputListener il;
	
	public InputHandler(InputListener il,Screen screen) {
		this.il = il;
		this.screen = screen;
	}
	
	public void handle(final int event,MouseEvent mouse) {
		switch (event) {
		case MouseEvent.MOUSE_PRESSED:
			screen.onMousePressed(mouse.getX(), mouse.getY());
			break;
		case MouseEvent.MOUSE_RELEASED:
			screen.onMouseRelease();
			break;
		case MouseEvent.MOUSE_DRAGGED:
			screen.onMouseDragged(mouse.getX(), mouse.getY());
			break;
		default:
			break;
		}
	}
}
