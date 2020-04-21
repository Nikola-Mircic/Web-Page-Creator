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
	
	/*Creating file*/
	public void createFile(String filename) {
		File file = new File(absolutePath+"\\"+filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating file!");
		}
	}
	
	public void createFile(String directory,String filename) {
		File file = new File(absolutePath+directory+"\\"+filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating file!");
		}
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
	
	private File findDirectory(File root,String dirname) {
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
		return findFile(new File(absolutePath+directory), filename);
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
	
	public static String readFile(File file) throws IOException {
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
	
	/*Functions dealing with .properties file:
	 *	createDefaultProperties() - It call in main function in Window class and create .properties file for the first time with default values
	 *	getProperties() - returns data from .properties
	 *	changeProperties(String propertieName,String newValue) - change value of field propertieName to newValue
	 *	makePropertie(String propertieName) -  create new field
	 *	makePropertie(String propertieName,String newValue) - create new field with value newValue*/
	public void createDefaultProperties() {
		if(findFile(".properties")==null) {
			File newProps = new File(absolutePath+"/.properties");
			try {
				newProps.createNewFile();
				makePropertie("author");
				makePropertie("default-project-name", "project");
				makePropertie("default-project-location", absolutePath+"\\projects");
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
}
