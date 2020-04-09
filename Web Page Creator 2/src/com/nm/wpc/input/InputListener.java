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
	private Screen screen;
	private InputHandler ih;
	
	public InputListener(Screen s) {
		this.screen = s;
		this.ih = new InputHandler(this, s);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		ih.handle(MouseEvent.MOUSE_PRESSED, e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		ih.handle(MouseEvent.MOUSE_RELEASED, e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		ih.handle(MouseEvent.MOUSE_DRAGGED, e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		screen.onKeyPressed(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
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
