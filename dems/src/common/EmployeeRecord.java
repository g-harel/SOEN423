package common;

public class EmployeeRecord extends Record {
	public String projectID;

	public EmployeeRecord(String firstName, String lastName, String id, String mailID, String projectID) {
		super(firstName, lastName, "ER" + id, mailID);
		
		this.projectID = projectID;
	}

}
