/*
 * Class: com.nm.wpc.editor.ElementSelector
 * Superclass : com.nm.editor.Editor
 * Used for: Creating a list of elements that the user can add to the page
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.nm.wpc.screen.InputPanel;
import com.nm.wpc.screen.WorkingScreen;
import com.nm.wpc.gui.*;
import com.nm.elems.Attribute;
import com.nm.elems.tagsystem.*;
import com.nm.wpc.editor.option.*;

public class ElementSelector extends Editor{
	private static final long serialVersionUID = 1L;

	private Color bckg;
	
	public ElementSelector(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		bckg = Color.WHITE;
		generateButtons();
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
	
	private	void generateButtons() {
		Button btn;
		Tag[] tags = Tag.values();
		int w = this.width,h = Math.min(this.height/tags.length, 50);
		int i;
		for(i=0;i<tags.length;++i) {
			btn = null;
			if(tags[i].name().length()>7) {
				if(tags[i].name().substring(0,7).equals("HEADING")){
					btn = new Button("HEADING", this.x, this.y+i*h, w, h, new Option(this.ws.getMs()){
						@Override
						public void make(GUIObject source) {
							InputPanel ip = new InputPanel(source,source.getX()-width/2,source.getY(),w*9/10,h*6);
							Button tempBtn;
							int s = findHeading(tags);
							for(int j=0;j<6;++j) {
								int s2 = j;
								tempBtn = new Button(tags[s+j].name(), ip.getX(), ip.getY()+j*h, w*9/10, h, new Option(){
									@Override
									public void make(GUIObject source) {
										List<Attribute> elemAttrs = tags[s+s2].getAttributes();
										System.out.println(((Button)source).getOption().getOptName());
										for(int k=0;k<elemAttrs.size();++k) {
											System.out.println("k:"+k);
											System.out.println(" *"+elemAttrs.get(k).getName());
										}
									}
								});
								ip.addGUIObject(tempBtn);
							}
							ip.drawContent();
							ms.drawPanel(ip);
						}
					});
					i+=6;
				}
			}
			if(btn==null){
				int s = i;
				btn = new Button(tags[s].name(), this.x, this.y+i*h, w, h, new Option(){
					@Override
					public void make(GUIObject source) {
						List<Attribute> elemAttrs = tags[s].getAttributes();
						System.out.println(((Button)source).getOption().getOptName());
						for(int k=0;k<elemAttrs.size();++k) {
							System.out.println(" *"+elemAttrs.get(k).getName());
						}
					}
				});
			}
			
			controler.addButton(btn);
		}
	}
	
	private int findHeading(Tag[] temp) {
		for(int i=0;i<temp.length;++i) {
			if(temp[i].name().equals("HEADING_1")) 
				return i;
		}
		return -1;
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		controler.activateOnClick(x, y);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		controler.releaseObjects();
		drawContent(width, height);
	}
}
