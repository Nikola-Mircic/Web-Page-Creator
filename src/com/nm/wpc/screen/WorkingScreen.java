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
import com.nm.elems.elements.Anchor;
import com.nm.elems.elements.TextBox;
import com.nm.elems.tagsystem.Tag;
import com.nm.wpc.editor.*;
import com.nm.wpc.gui.InputField;

public class WorkingScreen extends Screen{
	private static final long serialVersionUID = 1L;
	
	private MainScreen ms;
	private String projectName;
	
	
	private int editorsSize;
	private Editor[] editors;
	private int obIdx=0,esIdx=1,wpIdx=2,peIdx=3,eeIdx=4;
	
	public WorkingScreen(int w,int h,MainScreen ms) {
		super(w, h);
		this.ms = ms;
		projectName = "";
		
		this.editorsSize = 5;
		this.editors = new Editor[editorsSize];
		editors[wpIdx] = new WorkingPane(0, (int)(h*0.135), (int)(w*0.80), (int)(h*0.865),this);
		editors[obIdx] = new OptionsBar(0, 0, w, (int)(h*0.035), this);
		editors[esIdx] = new ElementSelector(0, (int)(h*0.035), w, (int)(h*0.10), this);
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
		PageElement newElement = getElement(newTag);
		((WorkingPane)editors[wpIdx]).addNew(newElement);
		((ElementEditor)editors[eeIdx]).setElementAttributes(newElement);
		drawContent(width, height);
	}
	
	public void createEditor(PageElement element) {
		((ElementEditor)editors[eeIdx]).setElementAttributes(element);
	}
	
	public void deleteFocusedElement() {
		((WorkingPane)editors[wpIdx]).deleteFocusedElement();
	}
	
	public void checkValues() {
		((ElementEditor)editors[eeIdx]).checkValues();
	}
	
	private PageElement getElement(Tag tag) {
		switch (tag) {
		case TEXT_BOX:
			return new TextBox(tag);
		case ANCHOR:
			return new Anchor(tag);
		default:
			if(tag == Tag.HEADING_1 || tag == Tag.HEADING_2 || tag == Tag.HEADING_3 ||
			   tag == Tag.HEADING_4 || tag == Tag.HEADING_5 || tag == Tag.HEADING_6) {
				return new TextBox(tag);
			}
			return new PageElement(tag);
		}
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		if(x>(width-66) && y>(height-64))
			ms.changeContent();
		
		if(editors[eeIdx].findEditingField()!=null)
			((ElementEditor)this.editors[eeIdx]).checkValues();
		
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
	public void onMouseDragged(int x, int y) {
		editors[wpIdx].onMouseDragged(x, y);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseWheel(int x,int y,int d) {
		for(int i=0;i<editorsSize;++i) {
			if(editors[i].x < x && (editors[i].x+editors[i].width)>x && editors[i].y < y && (editors[i].y+editors[i].height)>y)
				editors[i].onMouseWheel(x,y,d);
		}
		drawContent(width, height);
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
		editors[wpIdx] = new WorkingPane(0, (int)(height*0.135), (int)(width*0.80), (int)(height*0.865), this,projectName);
		editors[obIdx] = new OptionsBar(0, 0, width, (int)(height*0.035), this); 
		drawContent(width, height);
	}
	
	public WorkingPane getWorkingPane() {
		return ((WorkingPane)editors[wpIdx]);
	}
}