/*  Copyright 2020 Nikola Mircic
  
    This file is part of Web Page Creator.

    Web Page Creator is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Web Page Creator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Web Page Creator.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * Class: com.nm.wpc.input.InputListener
 * Superclass :  
 * Used for: getting input form user
 */

package com.nm.wpc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.nm.wpc.screen.Screen;

public class InputListener implements KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{
	private InputHandler ih;
	
	public InputListener(Screen s) {
		this.ih = new InputHandler(this, s);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		ih.handle(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		ih.handle(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		ih.handle(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		ih.handle(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		ih.handle(e);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
