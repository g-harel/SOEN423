package common;

import java.util.Random;

public class Record {
	protected String type = "";
	
	public String recordID;
	public String firstName;
	public String lastName;
	public int employeeID;
	public String mailID;
	
	public Record(String firstName, String lastName, int employeeID, String mailID) {
		this.recordID = this.type + String.format("%05d", new Random().nextInt(10^5));
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailID = mailID;
	}
}
