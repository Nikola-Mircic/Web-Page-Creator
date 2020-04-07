package com.nm.wpc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.nm.wpc.screen.MainScreen;
import com.nm.wpc.screen.Screen;

public class InputListener implements KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{
	private Screen screen;
	
	public InputListener(Screen s) {
		this.screen = s;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		screen.onClick(e.getX(), e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		((MainScreen)screen).onRelease();
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void keyPressed(KeyEvent arg0) {
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
