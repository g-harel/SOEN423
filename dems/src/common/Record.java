package common;

import java.util.Random;

public class Record {
	public String recordID;

	public int employeeID;
	public String firstName;
	public String lastName;
	public String mailID;
	
	public Record(String firstName, String lastName, String id, String mailID) {
		if (!id.matches("[A-Z]{2}\\d{5}")) {
			throw new Error("Invalid RecordID");
		}
		
		this.employeeID = new Random().nextInt(10^8);
		this.firstName = firstName;
		this.lastName = lastName;
		this.recordID = id;
		this.mailID = mailID;
	}
}
