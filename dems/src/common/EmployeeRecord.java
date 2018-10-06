package common;

public class EmployeeRecord extends Record {
	protected String type = "ER";
	
	public String projectID;

	public EmployeeRecord(String firstName, String lastName, int employeeID, String mailID, String projectID) {
		super(firstName, lastName, employeeID, mailID);
		
		this.projectID = projectID;
	}

}
