/*
 * Class: com.nm.wpc.input.InputHandler
 * Superclass :  
 * Used for: processing input form user
 */

package com.nm.wpc.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.nm.elems.PageElement;
import com.nm.elems.elements.TextBox;
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
		case MouseEvent.MOUSE_WHEEL:
			screen.onMouseWheel(mouse.getX(),mouse.getY(),((MouseWheelEvent)mouse).getWheelRotation()*10);
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
			case KeyEvent.VK_ENTER:
				if(screen instanceof MainScreen)
					((WorkingScreen)((MainScreen)screen).getActiveScreen()).checkValues();
				else
					screen.findEditingField().setEditing(false);
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
						if(focusedElement instanceof TextBox && e.isShiftDown())
							((TextBox)focusedElement).moveCursor(-1);
						else
							focusedElement.setX(focusedElement.getX()-10);
						break;
					case KeyEvent.VK_RIGHT:
						if(focusedElement instanceof TextBox && e.isShiftDown())
							((TextBox)focusedElement).moveCursor(1);
						else
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
					case KeyEvent.VK_BACK_SPACE:
						if(focusedElement instanceof TextBox)
							((TextBox)focusedElement).removeLetter();
						break;
					case KeyEvent.VK_SPACE:
						if(focusedElement instanceof TextBox)
							((TextBox)focusedElement).addLetter(' ');
						break;
					case KeyEvent.VK_ENTER:
						if(focusedElement instanceof TextBox)
							((TextBox)focusedElement).stopFocus();
						break;
					default:
						char c = e.getKeyChar();
						if(Character.isDefined(c))
							if(focusedElement instanceof TextBox)
								((TextBox)focusedElement).addLetter(c);
						break;
				}
			}
			screen.repaint();
		}
	}
}
