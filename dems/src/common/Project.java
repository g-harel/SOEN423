package common;

import java.util.HashMap;

public class Project extends HashMap<String, String> {
	
	public String projectID;
	public String clientName;
	public String projectName;
	
	public Project(String projectID, String clientName, String projectName) {
		this.projectID = projectID;
		this.clientName = clientName;
		this.projectName = projectName;
	}
	
}
