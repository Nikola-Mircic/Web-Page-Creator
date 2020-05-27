/*
 * Class: com.nm.wpc.screen.WorkinScreen
 * Superclass : 
 * Used for: Editing content in a project
 */

package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.editor.*;

public class WorkingScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private MainScreen ms;
	
	private int editorsSize;
	private Editor[] editors;
	
	public WorkingScreen(int w,int h,MainScreen ms) {
		super(w, h);
		this.ms = ms;
		
		this.editorsSize = 5;
		this.editors = new Editor[editorsSize];
		editors[0] = new OptionsBar(0, 0, w, (int)(h*0.035), this);
		editors[1] = new ElementSelector((int)(w*0.80), (int)(h*0.135), (int)(w*0.20), (int)(h*0.865), this);
		editors[2] = new WorkingPane(0, (int)(h*0.135), (int)(w*0.80), (int)(h*0.865), this);
		editors[3] = new PageEditor(0, (int)(h*0.035), w, (int)(h*0.10), this);
		editors[4] = new ElementEditor(0, (int)(h*0.035), w, (int)(h*0.10), this);
		this.drawContent(w,h);
	}
	
	@Override
	public void drawContent(int w,int h) {
		if(this.width != w || this.height != h)
			updateSize(w, h);
		this.width = w;
		this.height = h;
		
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = this.content.getGraphics();
		
		drawEditors(g);
		
		g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));
		g.fillRect(100, 200, 150, 150);
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	private void drawEditors(Graphics g) {
		for(int i=0;i<editorsSize;++i) {
			g.drawImage(this.editors[i].getContent(), this.editors[i].getX(), this.editors[i].getY(), null);
			if(editors[i] instanceof WorkingPane) {
				if(((WorkingPane)editors[i]).getFocused() != null) {
					i++;
				}else {
					g.drawImage(this.editors[++i].getContent(), this.editors[i].getX(), this.editors[i].getY(), null);
					break;
				}
			}
			
		}
	}
	
	private void updateSize(int newWidth,int newHeight) {
		for(int i=0;i<editorsSize;++i) {
			editors[i].drawContent(newWidth, newHeight);
		}
		editors[0] = new OptionsBar(0, 0, newWidth, (int)(newHeight*0.035), this);
		editors[1] = new ElementSelector((int)(newWidth*0.80), (int)(newHeight*0.135), (int)(newWidth*0.20), (int)(newHeight*0.865), this);
		editors[2] = new WorkingPane(0, (int)(newHeight*0.135), (int)(newWidth*0.80), (int)(newHeight*0.865), this);
		editors[3] = new PageEditor(0, (int)(newHeight*0.035), newWidth, (int)(newHeight*0.10), this);
		editors[4] = new ElementEditor(0, (int)(newHeight*0.035), newWidth, (int)(newHeight*0.10), this);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(x>(width-66) && y>(height-64))
			ms.changeContent();
		Editor temp;
		for(int i=0;i<this.editorsSize;++i) {
			temp = this.editors[i];
			if(temp.getX()<x && (temp.getX()+temp.getW())>x && temp.getY()<y && (temp.getY()+temp.getH())>y) {
				temp.onMousePressed(x, y);
				drawContent(this.width, this.height);
				break;
			}
		}
	}
	@Override
	public void onMouseRelease() {
		for(int i=0;i<this.editorsSize;++i) {
			this.editors[i].onMouseRelease();
		}
	}
}