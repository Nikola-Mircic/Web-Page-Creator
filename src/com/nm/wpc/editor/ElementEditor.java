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
	private Color bckg;
	
	public ElementEditor(int x, int y, int width, int height, WorkingScreen ws) {
		super(x, y, width, height, ws);
		
		this.setElementAttributes(new ArrayList<Attribute>());
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
		System.out.println("Genearating "+elementAttributes.size()+" fields...");
		controler.setObjects(new ArrayList<GUIObject>());
		int fWidth=0,fHeight=0;
		System.out.println(this.width+" "+this.height);
		List<List<Attribute>> toShow = new ArrayList<List<Attribute>>();
		for(int i=0;i<elementAttributes.size();++i) {
			toShow.add(new ArrayList<Attribute>());
		}
		String lastName = elementAttributes.get(0).getName();
		toShow.get(0).add(elementAttributes.get(0));
		int idx = 0;
		for(int i=1;i<elementAttributes.size();++i) {
			if(findGroup(elementAttributes.get(i).getName()).equals(lastName)) {
				toShow.get(idx).add(elementAttributes.get(i));
			}else {
				idx++;
				toShow.get(idx).add(elementAttributes.get(i));
				lastName = findGroup(elementAttributes.get(i).getName());
			}
		}
		for(int i=0;i<toShow.size();++i) {
			System.out.println("toShow["+i+"]:"+toShow.get(i).size());
		}
		fWidth = this.width;
		fHeight = 80;
		for(int i=0;i<toShow.size();++i) {
			if(toShow.get(i).isEmpty())
				break;
			if(toShow.get(i).size()>1) {
				int pos = i;
				int w = fWidth;
				int h = fHeight;
				controler.addButton(new Button(findGroup(toShow.get(pos).get(0).getName()), this.x, this.y+pos*fHeight, fWidth, fHeight, new Option(ws.getMs()) {
					@Override
					public void make(GUIObject source) {
						int ipw = source.getWidth()*8/10;
						int iph = toShow.get(pos).size()*h;
						int ipx = source.getX()-ipw*8/10;
						int ipy = source.getY();
						System.out.println((ipy+iph)+" ? "+(height));
						if(ipy+iph>height) {
							ipy = height-iph;
						}
						InputPanel ip = new InputPanel(source,ipx,ipy,ipw,iph);
						for(int j=0;j<toShow.get(pos).size();++j) {
							ip.addGUIObject(new InputField(toShow.get(pos).get(j).getName(), ip.getX(), ip.getY()+h*j, ip.getW(), h, 1));
						}
						ms.drawPanel(ip);
					}
				}));
			}else {
				controler.addObject(new InputField(toShow.get(i).get(0).getName(), this.x, this.y+i*fHeight, fWidth, fHeight, 1));
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
	int clickx,clicky;
	@Override
	public void onMousePressed(int x,int y) {
		clickx = x;
		clicky = y;
		bckg = new Color(135, 136, 139);
		controler.activateOnClick(x, y);
		drawContent(width, height);
	}
	
	@Override
	public void onMouseRelease() {
		bckg = new Color(185, 186, 189);
		controler.releaseObjects();
		if(controler.checkOnClick(clickx, clicky)!=null) {
			if(controler.checkOnClick(clickx, clicky) instanceof InputField) {
				if(((InputField)controler.checkOnClick(clickx, clicky)).isEditing()) {
					System.out.println("Working fine!!!!");
				}
			}
		}
		drawContent(width, height);
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
}
