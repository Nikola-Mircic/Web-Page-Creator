/*
 * Class: com.nm.wpc.editor.WorkingPane
 * Superclass : com.nm.editor.Editor
 * Used for: Showing the preview of the page
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.nm.elems.*;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.filesystem.ProjectManager;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.WorkingScreen;

public class WorkingPane extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;
	
	private String projectName;
	private Page page;
	private PageElement focused;
	private boolean typing;
	
	private int lastX=0,lastY=0;
	private byte actionCode = -1;
	
	public WorkingPane(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		bckg = new Color(136, 225, 247);
		
		this.page = new Page();
		this.focused = null;
		this.setTyping(false);
	}
	
	public WorkingPane(int x, int y, int width, int height, WorkingScreen ws,String projectName) {
		super(x, y, width, height, ws);
		bckg = new Color(136, 225, 247);
		
		this.page = new Page();
		this.focused = null;
		this.setTyping(false);
		this.setProjectName(projectName);
	}
	
	@Override
	public void drawContent(int width,int height) {
		setW(width);
		setH(height);
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		//Elements drawing
		page.drawElements(g);
		//Border for focusing element
		if(focused != null) {
			g.setColor(Color.BLUE);
			int x = focused.getX()+focused.getOffsetX();
			int y = focused.getY()+focused.getOffsetY();
			int w = focused.getWidth();
			int h = focused.getHeight();
			g.drawRect(x, y, w, h);
			g.fillRect(x-5, y-5, 10, 10);
			g.fillRect(x+w-5, y-5, 10, 10);
			g.fillRect(x+w-5, y+h-5, 10, 10);
			g.fillRect(x-5, y+h-5, 10, 10);
		}
	}
	
	public void addNew(PageElement newElement) {
		if(focused!=null) {
			focused.addElement(newElement);
		}else {
			page.addElement(newElement);
		}
		
		setFocused(newElement);
	}
	
	public void deleteFocusedElement() {
		page.deleteElement(focused);
		setFocused(null);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		this.lastX = x;
		this.lastY = y;
		x-=this.x;
		y-=this.y;
	
		if(focused!=null) {
			actionCode = focused.getActionCode(x, y);
		}
		
		if(actionCode < 1) {
			PageElement newFocused = page.findSelectedElement(x,y);
			
			if(newFocused != null) {
				this.ws.createEditor(newFocused);
			}
				
			setFocused(newFocused);
		}
		
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		this.lastX = 0;
		this.lastY = 0;
		if(actionCode != -1) {
			this.ws.createEditor(focused);
			this.actionCode = -1;
		}
	}
	
	@Override
	public void onMouseDragged(int x, int y) {
		if(!(x>this.x && x<(this.x + this.width) && y>this.y && y<(this.y + this.height)))
			return;
		if(focused == null)
			return;
		
		if(actionCode == -1)
			focused.getActionCode(x-this.x, y-this.y);
		
		switch (actionCode) {
		case 0:
			focused.setOffsetX(focused.getOffsetX()+x-lastX);
			focused.setOffsetY(focused.getOffsetY()+y-lastY);
			break;
		case 1:
			focused.setOffsetX(focused.getOffsetX()+x-lastX);
			focused.setOffsetY(focused.getOffsetY()+y-lastY);
			focused.setWidth(focused.getWidth()+lastX-x);
			focused.setHeight(focused.getHeight()+lastY-y);
			break;
		case 2:
			focused.setOffsetY(focused.getOffsetY()+y-lastY);
			focused.setWidth(focused.getWidth()+x-lastX);
			focused.setHeight(focused.getHeight()-y+lastY);
			break;
		case 3:
			focused.setWidth(focused.getWidth()+x-lastX);
			focused.setHeight(focused.getHeight()+y-lastY);
			break;
		case 4:
			focused.setOffsetX(focused.getOffsetX()+x-lastX);
			focused.setWidth(focused.getWidth()-x+lastX);
			focused.setHeight(focused.getHeight()+y-lastY);
			break;
		default:
			break;
		}
		
		lastX = x;
		lastY = y;
		
		drawContent(width,height);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public PageElement getFocused() {
		return focused;
	}

	public void setFocused(PageElement focused) {
		if(this.focused!=null)
			this.focused.stopFocus();
		this.focused = focused;
		if(this.focused!=null)
			this.focused.startFocus();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isTyping() {
		return typing;
	}

	public void setTyping(boolean typing) {
		this.typing = typing;
	}

}
