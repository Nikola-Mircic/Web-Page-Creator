package com.nm.wpc.window;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.nm.wpc.screen.Screen;

public class FormWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Screen s;

	public Screen getScreen() {
		return s;
	}

	public FormWindow(String title) {
		setTitle(title);
		setSize(new Dimension(300,500));
		setResizable(false);
		
		setLocationRelativeTo(null);
		
		s = new Screen(300, 500);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
	}

}
