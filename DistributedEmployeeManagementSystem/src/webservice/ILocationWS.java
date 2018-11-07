package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import location.ILocation;

@WebService
public interface ILocationWS extends ILocation {
	@WebMethod String createMRecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String project, String location);

	@WebMethod String createERecord(String managrID, String firstName, String lastName, int employeeID, String mailID, String projectID);

	@WebMethod String getRecordCounts(String managerID);

	@WebMethod String editRecord(String managerID, String recordID, String fieldName, String newValue);

	@WebMethod String transferRecord(String managerID, String recordID, String remoteCenterServerName);
}
