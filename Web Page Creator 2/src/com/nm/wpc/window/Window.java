package com.nm.wpc.window;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.nm.wpc.filesystem.FileManager;
import com.nm.wpc.screen.MainScreen;

/*
 * Web Page Creator [v2.0.0]
 * Created by:
 * Date: 9/2/2020
 * Time: 16:50
 * */

/*
 * Classname: Window
 * Used for: Creating frame of app
 */

public class Window extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	private static int WIDTH = 1000;
	private static int HEIGHT = 700;
	private String TITLE = "Web Page Creator 2.0.0";
	
	private static Thread app;
	private static MainScreen ms;
	
	public Window() {
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
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
		
		FileManager fm = new FileManager();
		fm.createDefaultProperties();
		
		ms = new MainScreen(WIDTH, HEIGHT);
		ms.updateSize(WIDTH, HEIGHT);
		Window window = new Window();
		
		window.add(ms);
		window.addKeyListener(ms.getListener());
		app = new Thread(window);
		app.start();
	}

	@Override
	public void run() {
		//this.add(ms);
		//this.addMouseListener(ms.getListener());
	}

}
