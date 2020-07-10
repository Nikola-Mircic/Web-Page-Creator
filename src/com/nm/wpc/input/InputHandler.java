/*
 * Class: com.nm.wpc.input.InputHandler
 * Superclass :  
 * Used for: processing input form user
 */

package com.nm.wpc.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.nm.elems.PageElement;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.screen.*;

public class InputHandler {
	private Screen screen;
	
	public InputHandler(InputListener il,Screen screen) {
		this.screen = screen;
	}
	
	public void handle(MouseEvent mouse) {
		switch (mouse.getID()) {
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
	
	public void handle(KeyEvent e) {
		InputField editing = null;
		if(screen instanceof MainScreen) {
			editing = ((MainScreen)screen).getActiveScreen().findEditingField();
			if(editing == null) {
				editing = ((MainScreen)screen).findEditingField();
			}
		}
		else {
			editing = screen.findEditingField();
		}
		if(editing != null) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				editing.removeLetter();
				break;
			case KeyEvent.VK_LEFT:
				editing.moveCursor(-1);
				break;
			case KeyEvent.VK_RIGHT:
				editing.moveCursor(1);
				break;
			case KeyEvent.VK_SPACE:
				editing.addLetter(' ');
				break;
			default:
				char c = e.getKeyChar();
				if(Character.isDefined(c))
					editing.addLetter(c);
				break;
			}
			screen.repaint();
		}else {
			PageElement focusedElement = null;
			Screen activeScreen = ((MainScreen)screen).getActiveScreen();
			if(activeScreen instanceof WorkingScreen) {
				focusedElement = ((WorkingScreen)activeScreen).findFocusedElement();
			}
			if(focusedElement != null) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						focusedElement.setX(focusedElement.getX()-10);
						break;
					case KeyEvent.VK_RIGHT:
						focusedElement.setX(focusedElement.getX()+10);
						break;
					case KeyEvent.VK_UP:
						focusedElement.setY(focusedElement.getY()-10);
						break;
					case KeyEvent.VK_DOWN:
						focusedElement.setY(focusedElement.getY()+10);
						break;
					case KeyEvent.VK_DELETE:
						if(activeScreen instanceof WorkingScreen)
							((WorkingScreen)activeScreen).deleteFocusedElement();
						break;
					default:
						break;
				}
			}
			screen.repaint();
		}
	}
}
