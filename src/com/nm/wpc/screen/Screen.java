package com.nm.wpc.screen;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.nm.wpc.gui.GUIControler;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.window.Window;


public abstract class Screen extends JPanel{
	private static final long serialVersionUID = 1L;
	
	protected Window window;
	
	protected int x,y;
	protected int width,height;
	
	protected BufferedImage content;
	
	protected boolean panelsActivity;
	
	protected GUIControler controler;
	protected List<InputPanel> panels;
	
	public Screen() {
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
	
	public Screen(int width,int height) {
		setW(width);
		setH(height);
		
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}

	public Screen(int width,int height,Window window) {
		setW(width);
		setH(height);
		setWindow(window);
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
	
	public Screen(int x,int y,int width,int height) {
		setX(x);
		setY(y);
		setW(width);
		setH(height);
		setWindow(window);
		this.controler = new GUIControler();
		this.panels = new ArrayList<InputPanel>();
	}
		
	protected void drawContent(int width,int height) {}
	
	public void onMousePressed(int x,int y) {}
	
	public void onMouseDragged(int x,int y) {}
	
	public void onMouseRelease() {}
	
	public void onKeyPressed(KeyEvent e) {}
	
	public void drawPanel(InputPanel ip) {
		this.panels.add(ip);
		this.panelsActivity = true;
	}
	
	protected void drawObjects(Graphics g) {
		controler.drawObjects(g,this.x,this.y);
	}
	
	public InputField findEditingField() {
		return controler.findEditingField(panels);
	}
	
	protected GUIObject checkOnClick(int x,int y) {
		return controler.checkOnClick(x,y);
	}
	
	protected void sendToBack(InputPanel panel){
		InputPanel temp = panel;
		panels.remove(panel);
		panels.add(temp);
	}
	
	public void addGUIObject(GUIObject object) {
		this.controler.addObject(object);
	}
	
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public BufferedImage getContent() {
		this.drawContent(width, height);
		return content;
	}

	public List<GUIObject> getObjects() {
		return controler.getObjects();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return width;
	}

	public void setW(int width) {
		this.width = width;
	}

	public int getH() {
		return height;
	}

	public void setH(int height) {
		this.height = height;
	}
}
