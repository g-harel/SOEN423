package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import location.Location;
import location.RecordServer;

@WebService
public class LocationWS implements ILocationWS {
	private Location location;

	public LocationWS(RecordServer rs) {
		this.location = new Location(rs);
	}

	@WebMethod
	public String createMRecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String project, String location) {
		return this.location.createMRecord(managerID, firstName, lastName, employeeID, mailID, project, location);
	}

	@WebMethod
	public String createERecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String projectID) {
		return this.location.createERecord(managerID, firstName, lastName, employeeID, mailID, projectID);
	}

	@WebMethod
	public String getRecordCounts(String managerID) {
		return this.location.getRecordCounts(managerID);
	}

	@WebMethod
	public String editRecord(String managerID, String recordID, String fieldName, String newValue) {
		return this.location.editRecord(managerID, recordID, fieldName, newValue);
	}

	@WebMethod
	public String transferRecord(String managerID, String recordID, String remoteCenterServerName) {
		return this.location.transferRecord(managerID, recordID, remoteCenterServerName);
	}
}
