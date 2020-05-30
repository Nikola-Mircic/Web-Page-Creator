package com.nm.wpc.filesystem;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Class: com.nm.wpc.filesystem.ProjectManger
 * Superclass : 
 * Used for: Organizing projects
 */

public class ProjectManager {
	private FileManager fm;
	private List<Project> projects;
	
	public ProjectManager() {
		fm = new FileManager();
		projects = new ArrayList<Project>();
		
		loadProjectData();
	}
	
	public void createNewProject(String name,String location,String ep) {
		char separator = File.separatorChar;
		fm.createDir(location, separator+name);
		fm.createDir(location+separator+name, "settings");
		fm.createFile(location+separator+name+"/settings", "project-settings.txt");
		fm.createDir(location+separator+name, "src");
		fm.createFile(location+separator+name+"/src",ep);
		
		createProjectData(name, location, ep);
	}
	
	private void loadProjectData() {
		String data = fm.getProjectData();
		this.projects = getProjects(data);
	}
	
	private void createProjectData(String name,String location,String ep) {
		Project project = new Project();
		Calendar date = Calendar.getInstance();
		
		project.setData("name", name);
		project.setData("location", location);
		project.setData("ep", ep);
		project.setData("dateCreated", date.getTime().toString());
		project.setData("dateModified", date.getTime().toString());
		
		this.projects.add(project);
		fm.addProjectData(project.getProjectData());
	}
	
	private List<Project> getProjects(String data){
		List<Project> temp = new ArrayList<Project>();
		
		int idx = 0;
		//Separator @|@
		for(int i=0;i<data.length();i++) {
			if(data.charAt(i)=='@') {
				if(data.substring(i, i+3).equals("@|@")) {
					temp.add(new Project(data.substring(idx, i)));
					i+=2;
					idx = i+1;
				}
			}
		}
		
		return temp;
	}
	
	public List<Map<String,String>> getRecentProject() throws ParseException{
		List<Map<String,String>> recent = new ArrayList<Map<String,String>>();
		int n = Math.min(5, projects.size());
		projects.sort(new Comparator<Project>() {
			@Override
			public int compare(Project a,Project b) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Calendar aDate = Calendar.getInstance();
				try {
					aDate.setTime(sdf.parse(a.getProjectMap().get("dateModified")));
				} catch (ParseException e) {
					return 0;
				}
				Calendar bDate = Calendar.getInstance();
				try {
					aDate.setTime(sdf.parse(b.getProjectMap().get("dateModified")));
				} catch (ParseException e) {
					return 0;
				}
				
				return (aDate.after(bDate)?1:0);
			}
		});
		for(int i=0;i<n;++i)
			recent.add(projects.get(i).getProjectMap());
		
		return recent;
	}
}

class Project{
	private Map<String,String> data;
	
	public Project() {
		this.data = getProjectBase();
	}
	
	public Project(String rawData) {
		this.data = parseProjectData(rawData);
	}
	
	private Map<String,String> getProjectBase(){
		Map<String,String> temp = new LinkedHashMap<String, String>();
		
		temp.put("location", "");
		temp.put("name", "");
		temp.put("dateCreated", "");
		temp.put("dateModified", "");
		temp.put("ep", "");
		
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
		
		for(Map.Entry<String, String> entry : this.data.entrySet()) {
			pData+=((String)entry.getValue()+"%|%");
		}
		
		pData+="@|@";
		
		return pData;
	}
	
	public Map<String, String> getProjectMap() {
		return this.data;
	}
	
	public void setData(String dataName,String value) {
		this.data.put(dataName, value);
	}
	
}
