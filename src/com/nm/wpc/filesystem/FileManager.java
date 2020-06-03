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

import com.nm.elems.Attribute;

public class FileManager {
	private String absolutePath;
	
	/*Constructors:
	 *	FileManager() - for a default project path
	 *	FileManager(String dir) - for using function inside a directory which is given by an absolute path dir */
	public FileManager() {
		File path = new File("");
		this.absolutePath = path.getAbsolutePath();
	}
	
	public FileManager(String dir) {
		this.absolutePath = dir;
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
		File file = new File(directory+"/"+filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			
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
		return findFile(new File(absolutePath), filename);
	}
	
	public File findFile(String directory,String filename) {
		return findFile(new File(directory), filename);
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
		StringBuilder sb = new StringBuilder();
		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null) {
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
		createDefaultProperties();//Continue with this!!!!
	}
	
	/*Functions dealing with .properties file:
	 *	createDefaultProperties() - It call in main function in Window class and create .properties file for the first time with default values
	 *	getProperties() - returns data from .properties
	 *	changeProperties(String propertieName,String newValue) - change value of field propertieName to newValue
	 *	makePropertie(String propertieName) -  create new field
	 *	makePropertie(String propertieName,String newValue) - create new field with value newValue*/
	private void createDefaultProperties() {
		if(findFile(".properties")==null) {
			File newProps = new File(absolutePath+"/.properties");
			try {
				newProps.createNewFile();
				makePropertie("author");
				makePropertie("default-project-name", "project");
				makePropertie("default-project-location", absolutePath+"/projects");
				makePropertie("default-font-size","20");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getProperties() {
		try {
			return readFile(findFile(".properties"));
		} catch (IOException e) {
			return "";
		}
	}
	
	public void changeProperties(String propertieName,String newValue) {
		String fileData = getProperties();
		if(fileData=="")
			return;
		
		int idxOfPropertie = fileData.indexOf(propertieName);
		if(idxOfPropertie == -1)
			return;
		
		int idxOfValue = idxOfPropertie + propertieName.length()+1;
		fileData = fileData.substring(0, idxOfValue)+newValue+","+fileData.substring(idxOfValue+fileData.substring(idxOfValue).indexOf(",")+1);
		try {
            Files.write(Paths.get(findFile(".properties").getAbsolutePath()), fileData.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
	}
	
	public void makePropertie(String propertieName) {
		String fileData = getProperties();
		fileData += (propertieName+":"+",\n");
		try {
            Files.write(Paths.get(findFile(".properties").getAbsolutePath()), fileData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void makePropertie(String propertieName,String newValue) {
		String fileData = getProperties();
		fileData += (propertieName+":"+newValue+",\n");
		try {
            Files.write(Paths.get(findFile(".properties").getAbsolutePath()), fileData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
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
	
	public void addProjectData(String data) {
		String fileData = getProjectData();
		fileData+=data;
		try {
			Files.write(Paths.get(findFile("projects.dat").getAbsolutePath()), fileData.getBytes());
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
	
	public Attribute[] getAttributes() {
		Attribute[] attrs = new Attribute[12];
		String attributes = "";
		try {
			attributes = readFile(findFile("attribute.dat"));
		} catch (IOException e) {
			return new Attribute[0];
		}
		
		int i=0,idx = attributes.indexOf('\n');
		while(idx>0) {
			String line = attributes.substring(0, idx);
			if(line.charAt(0) != '#')
				attrs[i++] = new Attribute(line.substring(0, line.indexOf(":,")),"");
			attributes = attributes.substring(idx+1);
			idx = attributes.indexOf('\n');
		}

		return attrs;
	}
	
}
