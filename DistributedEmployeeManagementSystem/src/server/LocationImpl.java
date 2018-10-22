package server;

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

    private String address;
	private RecordStore records;

	public LocationImpl(String address) {
		this.address = address;
		this.records = new RecordStore();
	}

	public String address() {
		return this.address;
	}

	public synchronized String createMRecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String project, String location) {
		if (!Validator.isFirstName(firstName)) {
			return Logger.log("[%s] error creating manager record, invalid first name \"%s\"", managerID, firstName);
		}
		if (!Validator.isLastName(lastName)) {
			return Logger.log("[%s] error creating manager record, invalid last name \"%s\"", managerID, lastName);
		}
		if (!Validator.isEmployeeID(employeeID)) {
			return Logger.log("[%s] error creating manager record, invalid employee ID \"%d\"", managerID, employeeID);
		}
		if (!Validator.isEmail(mailID)) {
			return Logger.log("[%s] error creating manager record, invalid mail ID \"%s\"", managerID, mailID);
		}
		if (!Validator.isLocationCode(location)) {
			return Logger.log("[%s] error creating manager record, invalid location code \"%s\"", managerID, location);
		}

		Project p = new Project(project);
		if (!Validator.isProjectID(p.projectID)) {
			return Logger.log("[%s] error creating manager record, invalid project ID \"%s\"", managerID, p.projectID);
		}
		if (!Validator.isClientName(p.clientName)) {
			return Logger.log("[%s] error creating manager record, invalid client name \"%s\"", managerID, p.clientName);
		}
		if (!Validator.isProjectName(p.projectName)) {
			return Logger.log("[%s] error creating manager record, invalid project name \"%s\"", managerID, p.projectName);
		}

		ManagerRecord record = new ManagerRecord();
		record.firstName = firstName;
		record.lastName = lastName;
		record.employeeID = employeeID;
		record.mailID = mailID;
		record.projectInfo = p;
		record.location = location;

		this.records.write(record);

		return Logger.log("[%s] manager record with id \"%s\" created for \"%s %s\"", managerID, record.recordID, firstName, lastName);
	}

	public synchronized String createERecord(String managerID, String firstName, String lastName, int employeeID, String mailID, String projectID) {
		if (!Validator.isFirstName(firstName)) {
			return Logger.log("[%s] error creating employee record, invalid first name \"%s\"", managerID, firstName);
		}
		if (!Validator.isLastName(lastName)) {
			return Logger.log("[%s] error creating employee record, invalid last name \"%s\"", managerID, lastName);
		}
		if (!Validator.isEmployeeID(employeeID)) {
			return Logger.log("[%s] error creating employee record, invalid employee ID \"%d\"", managerID, employeeID);
		}
		if (!Validator.isEmail(mailID)) {
			return Logger.log("[%s] error creating employee record, invalid mail ID \"%s\"", managerID, mailID);
		}
		if (!Validator.isProjectID(projectID)) {
			return Logger.log("[%s] error creating employee record, invalid project ID \"%s\"", managerID, projectID);
		}

		EmployeeRecord record = new EmployeeRecord();
		record.firstName = firstName;
		record.lastName = lastName;
		record.employeeID = employeeID;
		record.mailID = mailID;
		record.projectID = projectID;

		this.records.write(record);

		return Logger.log("[%s] employee record with id \"%s\" created for \"%s %s\"", managerID, record.recordID, firstName, lastName);
	}

	public synchronized String getRecordCounts(String managerID) {
		// TODO query other servers
		return String.format(this.address, this.records.count());
	}

	public synchronized String editRecord(String managerID, String recordID, String fieldName, String newValue) {
		Record record = this.records.read(recordID);
		if (record == null) {
			return Logger.log("[%s] error editing record, no record with id \"%s\" found", managerID, recordID);
		}

		if (fieldName.equals("mailID")) {
			if (!Validator.isEmail(newValue)) {
				return Logger.log("[%s] error editing record, invalid mail ID", managerID);
			}
			record.mailID = newValue;
			return Logger.log("[%s] updated mail ID", managerID);
		}

		if (Validator.isEmployeeRecordID(recordID)) {
			if (fieldName.equals("projectID")) {
				if (!Validator.isProjectID(newValue)) {
					return Logger.log("[%s] error editing record, invalid project ID", managerID);
				}
				((EmployeeRecord)record).projectID = newValue;
				return Logger.log("[%s] updated projectID", managerID);
			}
		}

		if (Validator.isManagerRecordID(recordID)) {
			if (fieldName.equals("location")) {
				if (!Validator.isLocationCode(newValue)) {
					return Logger.log("[%s] error editing record, invalid location", managerID);
				}
				((ManagerRecord)record).location = newValue;
				return Logger.log("[%s] updated location");
			}
			if (fieldName.equals("projectInfo.clientName")) {
				if (!Validator.isClientName(newValue)) {
					return Logger.log("[%s] error editing record, invalid client name", managerID);
				}
				((ManagerRecord)record).projectInfo.clientName = newValue;
				return Logger.log("[%s] updated project client's name", managerID);
			}
			if (fieldName.equals("projectInfo.projectName")) {
				if (!Validator.isFirstName(newValue)) {
					return Logger.log("[%s] error editing record, invalid client name", managerID);
				}
				((ManagerRecord)record).projectInfo.projectName = newValue;
				return Logger.log("[%s] updated project name", managerID);
			}
		}

		return Logger.log("error editing record, no editable field \"%s\" on record with ID \"%s\"", fieldName, recordID);
	}

	public String transferRecord(String managerID, String recordID, String remoteCenterServerName) {
		// TODO Auto-generated method stub
		return null;
	}
}
