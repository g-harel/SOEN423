package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA.ORB;

import DEMS.LocationPOA;
import common.EmployeeRecord;
import common.Logger;
import common.ManagerRecord;
import common.Project;
import common.Record;
import common.RecordStore;
import common.Validator;

public class LocationImpl extends LocationPOA {
    private ORB orb;

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    public void shutdown() {
        this.orb.shutdown(false);
    }

    ///

    private String location;
	private RecordStore records;
	
	public LocationImpl(String location) throws Exception {
		// TODO validate location
		this.location = location;
		this.records = new RecordStore();
		
		Logger.file("server-" + location);
	}

	public synchronized boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project, String location) throws Exception {
		ManagerRecord record = new ManagerRecord();
		record.firstName = firstName;
		record.lastName = lastName;
		record.employeeID = employeeID;
		record.mailID = mailID;
		record.projectInfo = project;
		record.location = location;

		this.records.write(record);

		Logger.log("manager record with id \"%s\" created for \"%s %s\"", record.recordID, firstName, lastName);

		return true;
	}

	public synchronized boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID) throws Exception {
		EmployeeRecord record = new EmployeeRecord();
		record.firstName = firstName;
		record.lastName = lastName;
		record.employeeID = employeeID;
		record.mailID = mailID;
		record.projectID = projectID;

		this.records.write(record);

		Logger.log("employee record with id \"%s\" created for \"%s %s\"", record.recordID, firstName, lastName);

		return true;
	}

	public synchronized String getRecordCounts() throws Exception {
		this.tryToConnect();
		String res = "";
		for (CenterServerInterface value : this.servers.values()) {
		    res += value.getSelfRecordCounts() + ", ";
		}
		return res + this.getSelfRecordCounts();
	}

	public synchronized String editRecord(String recordID, String fieldName, String newValue) throws Exception {
		Record record = this.records.read(recordID);
		if (record == null) {
			return Logger.log("error editing record, no record with id \"%s\" found", recordID);
		}

		if (fieldName.equals("mailID")) {
			if (!Validator.isEmail(newValue)) {
				return Logger.log("error editing record, invalid mailID");
			}
			record.mailID = newValue;
			return Logger.log("updated mailID");
		}

		if (Validator.isEmployeeID(recordID)) {
			if (fieldName.equals("projectID")) {
				if (!Validator.isProjectID(newValue)) {
					return Logger.log("error editing record, invalid projectID");
				}
				((EmployeeRecord)record).projectID = newValue;
				return Logger.log("updated projectID");
			}
		}

		if (Validator.isManagerID(recordID)) {
			if (fieldName.equals("location")) {
				if (!Validator.isLocationCode(newValue)) {
					return Logger.log("error editing record, invalid location");
				}
				((ManagerRecord)record).location = newValue;
				return Logger.log("updated location");
			}
			if (fieldName.equals("projectInfo.clientName")) {
				if (!Validator.isFirstName(newValue) && !Validator.isLastName(newValue)) {
					return Logger.log("error editing record, invalid client name");
				}
				((ManagerRecord)record).projectInfo.clientName = newValue;
				return Logger.log("updated project client's name");
			}
			if (fieldName.equals("projectInfo.projectName")) {
				if (!Validator.isFirstName(newValue) && !Validator.isLastName(newValue)) {
					return Logger.log("error editing record, invalid client name");
				}
				((ManagerRecord)record).projectInfo.projectName = newValue;
				return Logger.log("updated project name");
			}
		}

		return Logger.log("error editing record, no editable field \"%s\" on record with id \"%s\"", fieldName, recordID);
	}
}
