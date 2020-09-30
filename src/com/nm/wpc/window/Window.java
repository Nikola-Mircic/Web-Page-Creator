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

/*Classname: com.nm.wpc.window.Window
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
	private final String TITLE = "Web Page Creator <beta 9.30.20>";
	
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
