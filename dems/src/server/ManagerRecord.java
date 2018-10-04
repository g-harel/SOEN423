package server;

public class ManagerRecord extends Record {
	public Project projectInfo;
	public String location;

	public ManagerRecord(String firstName, String lastName, String id, String mailID, Project projectInfo, String location) {
		super(firstName, lastName, "ER" + id, mailID);
		
		this.projectInfo = projectInfo;
		this.location = location;
	}

}
