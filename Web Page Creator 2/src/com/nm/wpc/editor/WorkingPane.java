package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.nm.elems.*;
import com.nm.wpc.screen.WorkingScreen;

public class WorkingPane extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;
	
	private Page page;
	private PageElement focused;
	private boolean typing;
	
	public WorkingPane(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		bckg = new Color(136, 225, 247);
		
		this.page = null;//for now
		this.focused = null;
		this.setTyping(false);
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		bckg = new Color(136, 181, 181);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = new Color(136, 225, 247);
		drawContent(width, height);
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
		this.focused = focused;
	}

	public boolean isTyping() {
		return typing;
	}

	public void setTyping(boolean typing) {
		this.typing = typing;
	}

}
