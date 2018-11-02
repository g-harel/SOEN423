package corba;

import corba.EmployeeManagementSystem.Location;
import location.ILocation;

public class LocationRef implements ILocation {
	public Location location;
	
	public LocationRef(Location location) {
		this.location = location;
	}

	public String createMRecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String project, String location) {
		return this.location.createMRecord(managerID, firstName, lastName, employeeID, mailID, project, location);
	}

	public String createERecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String projectID) {
		return this.location.createERecord(managerID, firstName, lastName, employeeID, mailID, projectID);
	}

	public String getRecordCounts(String managerID) {
		return this.location.getRecordCounts(managerID);
	}

	public String editRecord(String managerID, String recordID, String fieldName, String newValue) {
		return this.location.editRecord(managerID, recordID, fieldName, newValue);
	}

	public String transferRecord(String managerID, String recordID, String remoteCenterServerName) {
		return this.location.transferRecord(managerID, recordID, remoteCenterServerName);
	}
}
