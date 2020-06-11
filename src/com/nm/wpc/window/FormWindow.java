package com.nm.wpc.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

import com.nm.wpc.screen.Screen;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.*;
import com.nm.wpc.input.InputListener;

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
	
	public void submit() {
		System.out.println("Submiting...");
		ProjectManager pm = new ProjectManager();
		List<GUIObject> inputs = p.getObjects();
		pm.createNewProject(((InputField)inputs.get(0)).getText(), ((InputField)inputs.get(1)).getText(), ((InputField)inputs.get(3)).getText());
		this.dispose();
	}
}

class Panel extends Screen{
	private static final long serialVersionUID = 1L;
	private GUIObject last;
	private InputListener listener;
	
	public Panel() {
		super();
		
		listener = new InputListener(this);
		this.addMouseListener(listener);
	}
	
	public Panel(int w,int h) {
		super(w, h);
		
		listener = new InputListener(this);
		this.addMouseListener(listener);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		this.drawObjects(g);
	}
	
	@Override
	public void onMousePressed(int x, int y) {
		last = findEditingField();
		
		if(last != null) {
			((InputField)last).setEditing(false);
			last=null;
		}
		
		GUIObject object = checkOnClick(x, y);
		if(object != null) {
			object.mousePressed(x, y);
		}
		
		repaint();
	}
	
	@Override
	public void onMouseRelease() {
		if(last != null) {
			last.mouseReleased();
			last = null;
		}
		repaint();
	}

	public void addInputField(String label,int x,int y,int width,int height,int type) {
		InputField input = new InputField(label,x,y,width,height,type);
		addGUIObject(input);
	}
	
	public void addButton(String title,int x,int y,int width,int height,Option option) {
		Button btn = new Button(title, x, y, width, height, option);
		addGUIObject(btn);
	}
	
	public void addButton(int x,int y,int width,int height,Option option) {
		Button btn = new Button(x, y, width, height, option);
		addGUIObject(btn);
	}
	
	public Dimension getDimension() {
		for(GUIObject object : controler.getObjects()) {
			if(object.getWidth() > width)
				this.width = object.getWidth()+40;
		}
		
		this.height = controler.getObjects().get(controler.getObjects().size()-1).getY()+controler.getObjects().get(controler.getObjects().size()-1).getHeight()+40;
		
		return new Dimension(this.width,this.height);
	}

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener listener) {
		this.listener = listener;
	}
	
}
