package com.nm.wpc.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectManager {
	private FileManager fm;
	
	public ProjectManager() {
		fm = new FileManager();
	}
	
	public void createNewProject(String name,String location,String ep) {
		fm.createDir(location, "/"+name);
		fm.createDir(location+"/"+name, "settings");
		fm.createFile(location+"/"+name+"/settings", "project-settings.txt");
		fm.createDir(location+"/"+name, "src");
		fm.createFile(location+"/"+name+"/src",ep);
		
		createProjectData();
	}
	
	private void createProjectData() {
		String data;
		File projectData = fm.findFile("projects.dat");
		try {
			data = fm.readFile(projectData);
			System.out.println(data);
		} catch (IOException e) {
			return;
		}
	}
}

class Project{
	Map<String,String> data;
	
	public Project() {
		this.data = getProjectBase();
	}
	
	public Project(String rawData) {
		this.data = parseProjectData(rawData);
	}
	
	private Map<String,String> getProjectBase(){
		Map<String,String> temp = new HashMap<String, String>();
		
		temp.put("id","");
		temp.put("location", "");
		temp.put("name", "");
		temp.put("dateCreated", "");
		temp.put("dateModified", "");
		
		return temp;
	}
	
	private Map<String,String> parseProjectData(String rawData){
		Map<String,String> temp = getProjectBase();
		
		// "%|%" - separator
		int idx = 0;
		String data;
		for(Map.Entry<String, String> entry : temp.entrySet()) {
			data="";
			while (idx < rawData.length()) {
				if(rawData.charAt(idx) == '%') {
					if(rawData.substring(idx, idx+3).equals("%|%")) {
						entry.setValue(data);
						idx+=3;
						break;
					}
				}
				data += rawData.charAt(idx);
				idx++;
			}
		}
		
		return temp;
	}
	
	public String getProjectData() {
		String pData = "";
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
			pData += (entry.getValue()+"%|%");
		}
		
		return pData;
	}
	
}
