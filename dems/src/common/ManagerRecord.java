package common;

public class ManagerRecord extends Record {
	protected String type = "MR";
	
	public Project projectInfo;
	public Location location;

	public ManagerRecord(String firstName, String lastName, int employeeID, String mailID, Project projectInfo, Location location) {
		super(firstName, lastName, employeeID, mailID);
		
		this.projectInfo = projectInfo;
		this.location = location;
	}

}
