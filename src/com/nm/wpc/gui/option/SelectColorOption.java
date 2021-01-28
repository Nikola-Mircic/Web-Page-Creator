/*  Copyright 2020 Nikola Mircic
  
    This file is part of Web Page Creator.

    Web Page Creator is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Web Page Creator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Web Page Creator.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.nm.wpc.gui.option;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.UIManager;

import com.nm.wpc.editor.ElementEditor;
import com.nm.wpc.editor.PageEditor;
import com.nm.wpc.gui.Button;
import com.nm.wpc.gui.ColorField;
import com.nm.wpc.gui.GUIObject;
import com.nm.wpc.screen.MainScreen;

public class SelectColorOption extends Option {

	public SelectColorOption() {
		// TODO Auto-generated constructor stub
	}

	public SelectColorOption(MainScreen ms) {
		super(ms);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void make(GUIObject source) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		ColorField cf = (ColorField)((Button)source).getGuiObject();
		Color c = cf.getColor();
		c = JColorChooser.showDialog(null, "Select color", c, true);
		cf.setColor(c);
		if(cf.getContainer() instanceof ElementEditor)
			((ElementEditor)cf.getContainer()).checkValues();
		else if(cf.getContainer() instanceof PageEditor)
			((PageEditor)cf.getContainer()).checkValues();
	}
}
