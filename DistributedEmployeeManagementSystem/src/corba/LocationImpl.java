package corba;

import org.omg.CORBA.ORB;
import corba.EmployeeManagementSystem.LocationPOA;
import location.Location;
import location.RecordServer;

public class LocationImpl extends LocationPOA {
	private ORB orb;

	public void setORB(ORB orb) {
		this.orb = orb;
	}

	public void shutdown() {
		this.orb.shutdown(false);
	}

	///

	private Location location;

	public LocationImpl(RecordServer rs) {
		this.location = new Location(rs);
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
