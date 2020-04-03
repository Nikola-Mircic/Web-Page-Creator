package com.nm.wpc.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class FileManager {
	private String absolutePath;
	
	public FileManager() {
		File path = new File("");
		this.absolutePath = path.getAbsolutePath();
	}
	
	public FileManager(String dir) {
		this.absolutePath = dir;
	}
	
	private String findFile(File root,String filename) {
		Queue<File> dirs = new LinkedList<File>();
		for(File file:root.listFiles()) {
			if(file.isDirectory()) {
				dirs.add(file);
			}else if(file.getName() == filename) {
				try {
					return readFile(file);
				} catch (Exception e) {
					return "";
				}
				
			}
		}
		return "";
	}
	
	public String getConfigs() {
		return findFile(new File(this.absolutePath), ".config");
	}
	
	public String findFile(String filename) {
		return findFile(new File(absolutePath), filename);
	}
	
	public String findFile(String dir,String filename) {
		return findFile(new File(absolutePath+dir), filename);
	}
	
	public static String readFile(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + System.lineSeparator());
		}

		return sb.toString();
	}
}
