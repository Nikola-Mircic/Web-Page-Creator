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

/*
 * Class: com.nm.wpc.filesystem.FileManager
 * Superclass : 
 * Used for: work with files and directories
 */

package com.nm.wpc.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nm.elems.Attribute;

public class FileManager {
	private String absolutePath;
	private Attribute[] attrs;
	/*Constructors:
	 *	FileManager() - for a default project path
	 *	FileManager(String dir) - for using function inside a directory which is given by an absolute path dir */
	public FileManager() {
		File path = new File("");
		this.absolutePath = path.getAbsolutePath();
		createDefaultAttributes();
	}
	
	public FileManager(String dir) {
		this.absolutePath = dir;
		createDefaultAttributes();
	}
	
	public boolean validatePath(String path) {
		char folderSeparator = File.separatorChar;
		int idx = path.indexOf(folderSeparator);
		File root = new File(path.substring(0, idx));
		File temp = null;
		path = path.substring(idx+1);
		idx = path.indexOf(folderSeparator);
		while(idx != -1) {
			temp = findDirectory(root, path.substring(0, idx));
			if(temp != null){
				root = temp;
			}else {
				temp = new File(root.getAbsolutePath()+folderSeparator+path.substring(0, idx));
				if(!temp.mkdir())
					return false;
				root = temp;
			}
			path = path.substring(idx+1);
			idx = path.indexOf(folderSeparator);
		}
		temp = findDirectory(root, path);
		if(temp != null){
			return true;
		}else {
			temp = new File(root.getAbsolutePath()+folderSeparator+path);
			if(!temp.mkdir())
				return false;
		}
		return true;
	}
	
	/*Creating file*/
	public void createFile(String filename) {
		File file = new File(absolutePath+"/"+filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
		}
	}
	
	public void createFile(String directory,String filename) {
		if(!validatePath(directory))
			return;
		File file = new File(directory+File.separatorChar+filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*Creating file as a directory*/
	@SuppressWarnings("unused")
	public void createDir(String dirname) {
		File file = new File(absolutePath+"/"+dirname);
		boolean create = file.mkdir();
	}
	@SuppressWarnings("unused")
	public void createDir(String root,String dirname) {
		if(!validatePath(root))
			return;
		File file = new File(root+'/'+dirname);
		boolean create = file.mkdir();
	}
	
	/*Finding specific file or directory:
	 * 	findDirectory(String dirname) - finding a directory by it's name
	 *	findFile(String filename) - finding a file by it's name
	 *	findFile(String directory,String filename) - finding a file with name filename in a directory with name directory
	 *	findFile(File root,String filename) - for finding a file in a root directory given as an object
	 *	readFile(File file) - returns a data stored in the file*/
	public File findDirectory(String dirname) {
		return findDirectory(new File(absolutePath),dirname);
	}
	
	public File findDirectory(String path,String dirname) {
		return findDirectory(new File(path),dirname);
	}
	
	private File findDirectory(File root,String dirname) {
		if(root.listFiles().length == 0)
			return null;
		List<File> dirs = new LinkedList<File>();
		for(File file : root.listFiles()) {
			if(file.isDirectory()) {
				if(file.getName().equals(dirname))
					return file;
				else
					dirs.add(file);
			}
		}
		for(File file : dirs) {
			findDirectory(file, dirname);
		}
		return null;
	}
	
	public File findFile(String filename) {
		File temp = findFile(new File(absolutePath), filename);
		return temp;
	}
	
	public File findFile(String directory,String filename) {
		File temp = findFile(new File(directory), filename);
		return temp;
	}
	
	private File findFile(File root,String filename) {
		List<File> dirs = new LinkedList<File>();
		for(File file:root.listFiles()) {
			if(file.isDirectory()) {
				dirs.add(file);
			}else if(file.getName().equals(filename)) {
				return file;
			}
		}
		for (File file : dirs) {
			findFile(file,filename);
		}
		return null;
	}
	
	public String readFile(File file) throws IOException {
		if(file == null)
			return "";
		StringBuilder sb = new StringBuilder();
		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null) {
			if(line.charAt(0)!='#')
				sb.append(line + System.lineSeparator());
		}
		
		br.close();
		return sb.toString();
	}
	/*Functions dealing with WPC configuration files
	 * lines 170-239 : dealing with .properties
	 * lines 241-271 : dealing with projects.dat
	 * lines 280- : dealing with attribute.dat
	 * */
	
	public void createDefaultConfiguration() {
		if(findFile("projects.dat")==null) {
			File projects = new File(absolutePath+File.separatorChar+"projects.dat");
			try {
				projects.createNewFile();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}
	
	/*Functions dealing with projects.dat file
	 * createEmptyProjectsData() - creates empty projects.dat file
	 * addProjectData(String data) - inserts data to file
	 * getProjectData() - returns list of projects each represent by a string
	 **/
	
	public void createEmptyProjectsData() {
		File temp = new File("projects.dat");
		try {
			temp.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void addProjectData(Project data) {
		String fileData = getProjectData();
		fileData+=convertProjectData(data);
		String projectSettings = data.getData("name")+";"+data.getData("location")+";"+data.getData("ep")+";";
		try {
			Files.write(Paths.get(findFile("projects.dat").getAbsolutePath()), fileData.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			char sep = File.separatorChar;
			Files.write(Paths.get(data.getData("location")+sep+data.getData("name")+sep+"settings"+sep+"project-settings.txt"), projectSettings.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getProjectData() {
		try {
			return readFile(findFile("projects.dat"));
		} catch (IOException e) {
			return "";
		}
	}
	
	public String convertProjectData(Project project) {
		String pData = "";
		
		for(Map.Entry<String, String> entry : project.getProjectMap().entrySet()) {
			pData+=((String)entry.getValue()+"%|%");
		}
		
		pData+="@|@";
		
		return pData;
	}
	
	
	public void createDefaultAttributes() {
		attrs = new Attribute[10];
		attrs[0] = new Attribute("href","","");
		attrs[1] = new Attribute("font-size","","px");
		attrs[2] = new Attribute("font-family","","");
		attrs[3] = new Attribute("color","","");
		attrs[4] = new Attribute("background-color","","");
		attrs[5] = new Attribute("width","","px");
		attrs[6] = new Attribute("height","","px");
		attrs[7] = new Attribute("position","absolute","");
		attrs[8] = new Attribute("margin-top","","px");
		attrs[9] = new Attribute("margin-left","","px");
	}
	
	public Attribute[] getAttributes() {
		return attrs;
	}
	
}
