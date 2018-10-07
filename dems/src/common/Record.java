package common;

public class Record {
	
	public String recordID;
	public String firstName;
	public String lastName;
	public int employeeID;
	public String mailID;
	
	public Record(String recordID, String firstName, String lastName, int employeeID, String mailID) {
		this.recordID = recordID;
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailID = mailID;
	}
}
