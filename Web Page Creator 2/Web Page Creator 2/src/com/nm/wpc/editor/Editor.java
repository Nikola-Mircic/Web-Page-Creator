package com.nm.wpc.editor;

import java.awt.image.BufferedImage;
import java.util.List;

import com.nm.wpc.editor.option.Option;

public class Editor{
	protected BufferedImage image;
	protected List<Option> opts;
	
	protected BufferedImage getImage() {
		return image;
	}
	protected void drawImage() {}
}
