package com.nm.wpc.gui;

import java.awt.Graphics;

import com.nm.wpc.editor.option.Option;
import com.nm.wpc.screen.Screen;
import com.nm.wpc.editor.*;

public class NumberField extends InputField{
	private Button btnUp = null;//▲
	private Button btnDown = null;//▼
	
	public NumberField(int x, int y, int width, int height) {
		super(x, y, width, height);
		initButtons();
		
		drawImage();
	}
	
	public NumberField(String label, int x, int y, int width, int height, int type) {
		super(label, x, y, width, height, type);
		initButtons();
		
		drawImage();
	}
	
	private void initButtons() {
		if(this.width<=0 || this.height<=0)
			return;
		Option up = new Option() {
			@Override
			public void make(GUIObject source) {
				String data = ((InputField)((Button)source).getGuiObject()).getText();
				if(data == null)
					return;
				if(data.length()==0) {
					data = "0";
				}
				int temp = Integer.parseInt(data);
				data = String.valueOf(temp+1);
				
				((InputField)((Button)source).getGuiObject()).setValue(data);
				((InputField)((Button)source).getGuiObject()).getContainer();
				Screen container = ((InputField)((Button)source).getGuiObject()).container;
				if(container instanceof ElementEditor)
					((ElementEditor)container).checkValues();
				if(container instanceof PageEditor)
					((PageEditor)container).checkValues();	
			}
		};
		
		Option down = new Option() {
			@Override
			public void make(GUIObject source) {
				String data = ((InputField)((Button)source).getGuiObject()).getText();
				if(data == null)
					return;
				if(data.length()==0) {
					data = "0";
				}
				int temp = Integer.parseInt(data);
				data = String.valueOf(temp-1);
				
				((InputField)((Button)source).getGuiObject()).setValue(data);
			}
		};
		if(this.type == 0) {
			btnUp = new Button("▲", width-height/2, 0, height/2, height/2, up).setGuiObject(this);
			btnDown = new Button("▼", width-height/2, height/2, height/2, height/2, down).setGuiObject(this);
		}else{
			btnUp = new Button("▲", width-height/4-2, height/2, height/4, height/4-2, up).setGuiObject(this);
			btnDown = new Button("▼", width-height/4-2, height*3/4-1, height/4, height/4-2, down).setGuiObject(this);
		}
		
	}

	@Override
	protected void drawImage() {
		// TODO Auto-generated method stub
		super.drawImage();
		if(this.width<=0 || this.height<=0 || btnUp == null || btnDown == null)
			return;
		Graphics g = this.img.getGraphics();
		g.drawImage(btnUp.getImg(), btnUp.getX(), btnUp.getY(), null);
		g.drawImage(btnDown.getImg(), btnDown.getX(), btnDown.getY(), null);
	}

	@Override
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		super.mousePressed(x, y);
		x-=this.x;
		y-=this.y;
		if(btnUp.x<x && (btnUp.x+btnUp.width)>x && btnUp.y<y && (btnUp.y+btnUp.height)>y)
			btnUp.mousePressed(x, y);
		if(btnDown.x<x && (btnDown.x+btnDown.width)>x && btnDown.y<y && (btnDown.y+btnDown.height)>y)
			btnDown.mousePressed(x, y);
	}
	
	@Override
	public void mouseReleased() {
		btnUp.mouseReleased();
		btnDown.mouseReleased();
		drawImage();
	}
	
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		super.setText(text);
	}

	@Override
	public InputField setValue(String newValue) {
		// TODO Auto-generated method stub
		return super.setValue(newValue);
	}
	
	@Override
	public void setWidth(int newWidth) {
		super.setWidth(newWidth);
		initButtons();
		drawImage();
	}

}
