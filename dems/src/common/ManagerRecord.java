package common;

public class ManagerRecord extends Record {
	private static int count = 0;
	
	public Project projectInfo;
	public Location location;

	public ManagerRecord(String firstName, String lastName, int employeeID, String mailID, Project projectInfo, Location location) {
		super("MR" + String.format("%05d", ManagerRecord.count), firstName, lastName, employeeID, mailID);
		
		this.projectInfo = projectInfo;
		this.location = location;
		
		ManagerRecord.count ++;
	}

}
