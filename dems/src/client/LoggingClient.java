package client;

import java.io.IOException;

import common.CenterServerInterface;
import common.Logger;
import common.Project;

public class LoggingClient implements CenterServerInterface {

	private CenterServerInterface remote;

	public LoggingClient(CenterServerInterface remote) {
		this.remote = remote;
	}

	//

	public Boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project) throws IOException {
		Logger.log("attempting to create manager record for \"%s %s\"", firstName, lastName);
		Boolean res = this.remote.createMRecord(firstName, lastName, employeeID, mailID, project);
		if (res) {
			Logger.log("manager record created for \"%s %s\"", firstName, lastName);
		} else {
			Logger.log("failed to create manager record for \"%s %s\"", firstName, lastName);
		}
		return res;
	}

	public Boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID, String location) throws IOException {
		Logger.log("attempting to create employee record for \"%s %s\" in \"%s\" server", firstName, lastName, location);
		Boolean res = this.remote.createERecord(firstName, lastName, employeeID, mailID, projectID, location);
		if (res) {
			Logger.log("employee record created for \"%s %s\" in \"%s\" server", firstName, lastName, location);
		} else {
			Logger.log("failed to create employee record for \"%s %s\" in \"%s\" server", firstName, lastName, location);
		}
		return res;
	}

	public String getRecordCounts() throws IOException {
		Logger.log("attempting to read all record counts");
		String res = this.remote.getRecordCounts();
		return Logger.log(res);
	}

	public String editRecord(String recordID, String fieldName, String newValue) throws IOException {
		Logger.log("attempting to edit field \"%s\" on record with id \"%s\"", fieldName, recordID);
		String res = this.remote.editRecord(recordID, fieldName, newValue);
		return Logger.log(res);
	}

	// Should not be used, just exists to satisfy interface.
	public String getSelfRecordCounts() throws IOException {
		return null;
	}

}
