/*
 * Class: com.nm.wpc.screen.WorkinScreen
 * Superclass : 
 * Used for: Editing content in a project
 */

package com.nm.wpc.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.elems.PageElement;
import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.editor.*;
import com.nm.wpc.gui.InputField;

public class WorkingScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private MainScreen ms;
	
	private int editorsSize;
	private Editor[] editors;
	private int obIdx=0,esIdx=1,wpIdx=2,peIdx=3,eeIdx=4;
	
	public WorkingScreen(int w,int h,MainScreen ms) {
		super(w, h);
		this.ms = ms;
		
		this.editorsSize = 5;
		this.editors = new Editor[editorsSize];
		editors[obIdx] = new OptionsBar(0, 0, w, (int)(h*0.035), this);
		editors[esIdx] = new ElementSelector(0, (int)(h*0.035), w, (int)(h*0.10), this);
		editors[wpIdx] = new WorkingPane(0, (int)(h*0.135), (int)(w*0.80), (int)(h*0.865), this);
		editors[peIdx] = new PageEditor((int)(w*0.80), (int)(h*0.135), (int)(w*0.20), (int)(h*0.865), this);
		editors[eeIdx] = new ElementEditor((int)(w*0.80), (int)(h*0.135), (int)(w*0.20), (int)(h*0.865), this);
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
		
		g.setColor(Color.GREEN);
		g.fillRect(width-66, height-64, 50, 25);
	}
	
	private void drawEditors(Graphics g) {
		for(int i=0;i<3;++i) {
			g.drawImage(editors[i].getContent(), editors[i].getX(), editors[i].getY(), null);
		}
		if(((WorkingPane)editors[wpIdx]).getFocused() != null)
			g.drawImage(editors[eeIdx].getContent(), editors[eeIdx].getX(), editors[eeIdx].getY(), null);
		else
			g.drawImage(editors[peIdx].getContent(), editors[peIdx].getX(), editors[peIdx].getY(), null);
	}
	
	private void updateSize(int newWidth,int newHeight) {
		editors[obIdx].update(0, 0, newWidth, (int)(newHeight*0.035), this);
		editors[esIdx].update(0, (int)(newHeight*0.035), newWidth, (int)(newHeight*0.10), this);
		editors[wpIdx].update(0, (int)(newHeight*0.135), (int)(newWidth*0.80), (int)(newHeight*0.865), this);
		editors[peIdx].update((int)(newWidth*0.80), (int)(newHeight*0.135), (int)(newWidth*0.20), (int)(newHeight*0.865), this);
		editors[eeIdx].update((int)(newWidth*0.80), (int)(newHeight*0.135), (int)(newWidth*0.20), (int)(newHeight*0.865), this);
	}
	
	public void pickElement(Tag newTag) {
		System.out.println("Creating ["+newTag.getTagname()+"]["+newTag.name()+"] element...");
		PageElement newElement = new PageElement(newTag);
		System.out.println("Displaying element...");
		((WorkingPane)editors[wpIdx]).addNew(newElement);
		System.out.println("Creating editor...");
		((ElementEditor)editors[eeIdx]).setElementAttributes(newElement);
		drawContent(width, height);
	}
	
	public void checkValues(PageElement focused) {
		((ElementEditor)this.editors[eeIdx]).checkValues(focused);
		drawContent(width, height);
	}
	
	public void createEditor(PageElement element) {
		((ElementEditor)editors[eeIdx]).setElementAttributes(element);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(x>(width-66) && y>(height-64))
			ms.changeContent();
		
		Editor temp;
		for(int i=0;i<this.editorsSize;++i) {
			if(i==peIdx && ((WorkingPane)editors[wpIdx]).getFocused()!=null)
				continue;
			
			if(i==eeIdx && ((WorkingPane)editors[wpIdx]).getFocused()==null)
				continue;
				
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
	
	@Override
	public InputField findEditingField() {
		InputField inf = null; 
		for(int i=0;i<editorsSize;++i) {
			inf = editors[i].findEditingField();
			if(inf != null)
				return inf;
		}
		return inf;
	}
	
	public PageElement findFocusedElement() {
		return ((WorkingPane)editors[wpIdx]).getFocused();
	}

	public MainScreen getMs() {
		return ms;
	}

	public void setMs(MainScreen ms) {
		this.ms = ms;
	}
}