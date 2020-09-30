/*
 * Classname: com.nm.wpc.window.Window
 * Superclass:
 * Used for: Creating frame of app
 */

package com.nm.wpc.window;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.screen.MainScreen;

public class Window extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	private final String TITLE = "Web Page Creator <alpha 9.30.20>";
	
	private static MainScreen ms;
	
	public Window() {
		ms = new MainScreen(WIDTH, HEIGHT,this);
		
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
		FileManager fm = new FileManager();
		fm.createDefaultConfiguration();
		Thread wpc = new Thread(window);
		wpc.run();
	}

	@Override
	public void run() {
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setSize(WIDTH,HEIGHT);
		
		setTitle(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		this.add(ms);
		this.addKeyListener(ms.getListener());
	}

}
