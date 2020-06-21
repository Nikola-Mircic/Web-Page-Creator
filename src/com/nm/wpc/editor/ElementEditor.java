/*
 * Class: com.nm.wpc.editor.ElementEditor
 * Superclass : com.nm.editor.Editor
 * Used for: allows you to change attributes of single element and displays it
 */

package com.nm.wpc.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nm.elems.Attribute;
import com.nm.elems.PageElement;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.gui.InputField;
import com.nm.wpc.screen.*;
import com.nm.wpc.editor.option.*;

public class ElementEditor extends Editor{
	private static final long serialVersionUID = 1L;
	
	private List<Attribute> elementAttributes;
	private List<List<GUIObject>> toShow;
	private Color bckg;
	
	public ElementEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		this.setElementAttributes(new ArrayList<Attribute>());
		this.setToShow(new ArrayList<List<GUIObject>>());
		
		bckg = new Color(185, 186, 189);
		drawContent(width, height);
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
	
	private void generateObjects() {
		controler.setObjects(new ArrayList<GUIObject>());
		int fWidth=this.width,fHeight=80;
		
		toShow.add(new ArrayList<GUIObject>());
		
		String lastName = elementAttributes.get(0).getName();
		toShow.get(0).add(new InputField(elementAttributes.get(0).getName(),this.x,this.y,fWidth,fHeight,1));
		int idx = 0;
		for(int i=1;i<elementAttributes.size();++i) {
			if(findGroup(elementAttributes.get(i).getName()).equals(lastName)) {
				if(toShow.get(idx).size() == 1)
					toShow.get(idx).get(0).setWidth(fWidth*8/10);
				toShow.get(idx).add(new InputField(elementAttributes.get(i).getName(),this.x,this.y+toShow.get(idx).size()*fHeight,fWidth,fHeight,1));
			}else {
				toShow.add(new ArrayList<GUIObject>());
				idx++;
				toShow.get(idx).add(new InputField(elementAttributes.get(i).getName(),this.x,this.y+idx*fHeight,fWidth,fHeight,1));
				lastName = findGroup(elementAttributes.get(i).getName());
			}
		}
		
		for(int i=0;i<toShow.size();++i) {
			if(toShow.get(i).isEmpty())
				break;
			if(toShow.get(i).size()>1) {
				int pos = i;
				int w = fWidth;
				int h = fHeight;
				controler.addButton(new Button(findGroup(((InputField)toShow.get(pos).get(0)).getLabel()), this.x, this.y+pos*fHeight, fWidth, fHeight, new Option(ws.getMs()) {
					@Override
					public void make(GUIObject source) {
						int ipw = source.getWidth()*8/10;
						int iph = toShow.get(pos).size()*h;
						int ipx = source.getX()-ipw*8/10;
						int ipy = source.getY();
						if(ipy+iph>height) {
							ipy = height-iph;
						}
						
						InputPanel ip = new InputPanel(source,ipx,ipy,ipw,iph);
						for(int j=0;j<toShow.get(pos).size();++j) {
							toShow.get(pos).get(j).setWidth(ipw);
							toShow.get(pos).get(j).setX(ipx);
							toShow.get(pos).get(j).setY(ipy+j*h);
							ip.addGUIObject(toShow.get(pos).get(j));
						}
						
						ms.drawPanel(ip);
					}
				}));
			}else {
				controler.addObject(toShow.get(i).get(0));
			}
		}
		
		drawContent(width, height);
	}
	
	//Group contain attributes with the same beginning 
	private String findGroup(String attrName) {
		int idx = attrName.indexOf('-');
		if(idx > 0) {
			return attrName.substring(0, idx);
		}
		return attrName;
	}
	
	private void checkValues() {
		
	}
	
	@Override
	public void onMousePressed(int x,int y) {
		controler.activateOnClick(x, y);
		
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		controler.releaseObjects();
		checkValues();
		drawContent(width, height);
	}
	
	public void submitAttributeChange() {
		//Finish
	}

	public List<Attribute> getElementAttributes() {
		return elementAttributes;
	}
	
	public void setElementAttributes(PageElement element) {
		this.elementAttributes = element.getAttributes();
		generateObjects();
	}
	
	public void setElementAttributes(List<Attribute> elementAttributes) {
		this.elementAttributes = elementAttributes;
	}

	public List<List<GUIObject>> getToShow() {
		return toShow;
	}

	public void setToShow(List<List<GUIObject>> toShow) {
		this.toShow = toShow;
	}
}
