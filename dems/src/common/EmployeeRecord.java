package common;

public class EmployeeRecord extends Record {
	private static int count = 0; 
	
	public String projectID;

	public EmployeeRecord(String firstName, String lastName, int employeeID, String mailID, String projectID) {
		super("ER" + String.format("%05d", EmployeeRecord.count), firstName, lastName, employeeID, mailID);
		
		this.projectID = projectID;
		
		EmployeeRecord.count++;
	}

}
