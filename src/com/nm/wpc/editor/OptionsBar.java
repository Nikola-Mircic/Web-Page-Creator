/*
 * Class: com.nm.wpc.editor.OptionsBar
 * Superclass : com.nm.editor.Editor
 * Used for: Displaying some standard options for project or window
 */
package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nm.wpc.editor.option.ContinueRecentOption;
import com.nm.wpc.editor.option.NewProjectOption;
import com.nm.wpc.editor.option.OpenProjectOption;
import com.nm.wpc.editor.option.Option;
import com.nm.wpc.editor.option.SaveProjectOption;
import com.nm.wpc.gui.Button;
import com.nm.wpc.screen.WorkingScreen;

public class OptionsBar extends Editor {
	private static final long serialVersionUID = 1L;
	
	private Color bckg;

	public OptionsBar(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		bckg = Color.WHITE;
		genarateObjects();
	}
	
	@Override
	public void drawContent(int width,int height) {
		this.content = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = content.getGraphics();
		g.setColor(bckg);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		drawObjects(g);
	}
	
	private void genarateObjects() {
		Option[] options = new Option[4];
		options[0] = new NewProjectOption(ws.getMs());
		options[1] = new ContinueRecentOption(ws.getMs());
		options[2] = new OpenProjectOption(ws.getMs());
		if(ws.getWorkingPane().getPage()!= null && ws.getProjectName() != "")
			options[3] = new SaveProjectOption(ws.getMs(), ws.getWorkingPane().getPage(), ws.getProjectName());
		else
			options[3] = new SaveProjectOption(ws.getMs());
		for(int i=0;i<4;i++) {
			this.addGUIObject(new Button(i*150, 0, 150, this.height, options[i]));
		}
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		bckg = new Color(150, 150, 150);
		controler.activateOnClick(x, y);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = Color.WHITE;
		controler.releaseObjects();
		drawContent(width, height);
	}

}
