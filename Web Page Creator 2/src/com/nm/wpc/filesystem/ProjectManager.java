package com.nm.wpc.filesystem;

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
	}
}
