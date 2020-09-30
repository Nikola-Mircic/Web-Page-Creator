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

package com.nm.wpc.filesystem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.nm.elems.Page;
import com.nm.elems.loader.*;

/*
 * Class: com.nm.wpc.filesystem.ProjectManger
 * Superclass : 
 * Used for: Organizing projects
 */

public class ProjectManager {
	private FileManager fm;
	private PageLoader pl;
	private List<Project> projects;
	
	public ProjectManager() {
		fm = new FileManager();
		pl = new PageLoader();
		projects = new ArrayList<Project>();
		
		loadProjectData();
	}
	
	private void loadProjectData() {
		String data = fm.getProjectData();
		this.projects = new ArrayList<>();
		if(data == "")
			return;
		this.projects = getProjects(data);
	}
	
	public void createNewProject(String name,String location,String ep) {
		char separator = File.separatorChar;
		fm.createDir(location, separator+name);
		fm.createDir(location+separator+name, "settings");
		fm.createFile(location+separator+name+separator+"settings", "project-settings.txt");
		fm.createDir(location+separator+name, "src");
		fm.createFile(location+separator+name+separator+"src",ep);
		
		createProjectData(name, location, ep);
	}
	
	public void createProjectData(String name,String location,String ep) {
		Project project = new Project();
		Calendar date = Calendar.getInstance();
		
		project.setData("name", name);
		project.setData("location", location);
		project.setData("ep", ep);
		project.setData("dateCreated", date.getTime().toString());
		project.setData("dateModified", date.getTime().toString());
		
		this.projects.add(project);
		insertHTMLData(project, pl.createBlankPage().getPageCode());
		
		fm.addProjectData(project);
	}
	
	public void openProjectData(String data[]) {
		Project project = new Project();
		Calendar date = Calendar.getInstance();
		
		project.setData("name", data[0]);
		project.setData("location", data[1]);
		project.setData("ep", data[2]);
		project.setData("dateCreated", date.getTime().toString());
		project.setData("dateModified", date.getTime().toString());
		
		boolean exist = false;
		for(Project temp : projects) {
			if(temp.getData("name").equals(data[0])) {
				exist = true;
				break;
			}
		}
		
		if(!exist) {
			this.projects.add(project);
			fm.addProjectData(project);
		}
	}
	
	private List<Project> getProjects(String data){
		List<Project> temp = new ArrayList<Project>();
		
		int idx = 0;
		//Separator @|@
		for(int i=0;i<data.length();i++) {
			if(data.charAt(i)=='@') {
				if(data.substring(i, i+3).equals("@|@")) {
					temp.add(new Project(data.substring(idx, i)));
					i+=4;
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
	
	public void convertPageToHTML(Page toConvert,String projectName) {
		loadProjectData();
		for(Project project : projects) {
			if(project.getData("name").equals(projectName)) {
				char sep = File.separatorChar;
				String pageData = toConvert.getPageCode();
				try {
					Files.write(Paths.get(project.getData("location")+sep+project.getData("name")+sep+"src"+sep+project.getData("ep")), pageData.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void insetHTMLData(String projectName,String data) {
		loadProjectData();
		for(Project project : projects) {
			if(project.getData("name").equals(projectName)) {
				char sep = File.separatorChar;
				try {
					Files.write(Paths.get(project.getData("location")+sep+project.getData("name")+sep+"src"+sep+project.getData("ep")), data.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void insertHTMLData(Project project,String data) {
		for(Project p : projects) {
			if(p.getData("name").equals(project.getData("name"))) {
				char sep = File.separatorChar;
				try {
					Files.write(Paths.get(project.getData("location")+sep+project.getData("name")+sep+"src"+sep+project.getData("ep")), data.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getPageSource(String projectName) throws IOException {
		for(Project project : projects) {
			if(project.getData("name").equals(projectName)) {

				char sep = File.separatorChar;
				String path = project.getData("location")+sep+project.getData("name")+sep+"src"+sep+project.getData("ep");
				FileInputStream fs = new FileInputStream(path);
				DataInputStream in = new DataInputStream(fs);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line=br.readLine())!=null) {
					sb.append(line);
				}
				br.close();
				in.close();
				fs.close();
				
				return sb.toString();
			}
		}
		return "";
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
	
	public String getData(String name) {
		return this.data.get(name);
	}
	
	public void setData(String dataName,String value) {
		this.data.put(dataName, value);
	}
	
}
