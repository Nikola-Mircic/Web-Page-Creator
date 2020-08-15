package com.nm.wpc.window;

import javax.swing.JFrame;

import com.nm.wpc.screen.*;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.gui.*;

public class FormWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private String title;
	int width,height;
	
	private Panel p;

	public FormWindow(String title,int w,int h) {
		this.title = title;
		this.width = w;
		this.height = h;
		
		p = new Panel();
	}
	
	public void createWindow() {
		this.setTitle(title);
		this.setSize(p.getDimension());
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		
		this.add(p);
		this.addKeyListener(p.getListener());
		
		this.setVisible(true);
	}
	
	public void addInputField(InputField newIf) {
		p.getObjects().add(newIf);
	}
	
	public void addInputField(String label,int x,int y,int width,int height,int type) {
		InputField input = new InputField(label,x,y,width,height,type);
		p.getObjects().add(input);
	}
	
	public void addButton(Button btn) {
		p.getObjects().add(btn);
	}
	
	public void addButton(String title,int x,int y,int width,int height,Option option) {
		Button btn = new Button(title, x, y, width, height, option);
		p.getObjects().add(btn);
	}
	
	public void addButton(int x,int y,int width,int height,Option option) {
		Button btn = new Button(x, y, width, height, option);
		p.getObjects().add(btn);
	}
	
	public Panel getPanel() {
		return this.p;
	}
}
