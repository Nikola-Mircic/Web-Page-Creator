/*
 * Classname: com.nm.wpc.window.Window
 * Superclass:
 * Used for: Creating frame of app
 */

package com.nm.wpc.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import com.nm.wpc.screen.MainScreen;

public class Window extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 1000;
	public static int HEIGHT = 700;
	private String TITLE = "Web Page Creator 2.0.0";
	
	private static MainScreen ms;
	
	public Window() {
		Dimension startDimension = Toolkit.getDefaultToolkit().getScreenSize();
		ms = new MainScreen(startDimension.width, startDimension.height);
		
		this.getContentPane().addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				ms.updateSize(getWidth(), getHeight());
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public static void main(String[] args) {
		Window window = new Window();
		Thread wpc = new Thread(window);
		wpc.run();
	}

	@Override
	public void run() {
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setExtendedState(MAXIMIZED_BOTH);
		
		setTitle(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		this.add(ms);
		this.addKeyListener(ms.getListener());
	}

}
