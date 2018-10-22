import org.omg.CORBA.ORB;

import DEMS.LocationPOA;

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
			return Logger.err("[%s] invalid manager first name \"%s\"", managerID, firstName);
		}
		if (!Validator.isLastName(lastName)) {
			return Logger.err("[%s] invalid manager last name \"%s\"", managerID, lastName);
		}
		if (!Validator.isEmployeeID(employeeID)) {
			return Logger.err("[%s] invalid manager employee ID \"%d\"", managerID, employeeID);
		}
		if (!Validator.isEmail(mailID)) {
			return Logger.err("[%s] invalid manager mail ID \"%s\"", managerID, mailID);
		}
		if (!Validator.isLocationCode(location)) {
			return Logger.err("[%s] invalid manager location code \"%s\"", managerID, location);
		}

		Project p = new Project(project);
		if (!Validator.isProjectID(p.projectID)) {
			return Logger.err("[%s] invalid manager project ID \"%s\"", managerID, p.projectID);
		}
		if (!Validator.isClientName(p.clientName)) {
			return Logger.err("[%s] invalid manager client name \"%s\"", managerID, p.clientName);
		}
		if (!Validator.isProjectName(p.projectName)) {
			return Logger.err("[%s] invalid manager project name \"%s\"", managerID, p.projectName);
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
			return Logger.err("[%s] invalid employee first name \"%s\"", managerID, firstName);
		}
		if (!Validator.isLastName(lastName)) {
			return Logger.err("[%s] invalid employee last name \"%s\"", managerID, lastName);
		}
		if (!Validator.isEmployeeID(employeeID)) {
			return Logger.err("[%s] invalid employee employee ID \"%d\"", managerID, employeeID);
		}
		if (!Validator.isEmail(mailID)) {
			return Logger.err("[%s] invalid employee mail ID \"%s\"", managerID, mailID);
		}
		if (!Validator.isProjectID(projectID)) {
			return Logger.err("[%s] invalid employee project ID \"%s\"", managerID, projectID);
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
			return Logger.err("[%s] no record with id \"%s\" found", managerID, recordID);
		}

		if (fieldName.equals("mailID")) {
			if (!Validator.isEmail(newValue)) {
				return Logger.err("[%s] invalid mail ID", managerID);
			}
			record.mailID = newValue;
			return Logger.log("[%s] updated mail ID", managerID);
		}

		if (Validator.isEmployeeRecordID(recordID)) {
			if (fieldName.equals("projectID")) {
				if (!Validator.isProjectID(newValue)) {
					return Logger.err("[%s] invalid project ID", managerID);
				}
				((EmployeeRecord)record).projectID = newValue;
				return Logger.log("[%s] updated projectID", managerID);
			}
		}

		if (Validator.isManagerRecordID(recordID)) {
			if (fieldName.equals("location")) {
				if (!Validator.isLocationCode(newValue)) {
					return Logger.err("[%s] invalid location", managerID);
				}
				((ManagerRecord)record).location = newValue;
				return Logger.log("[%s] updated location");
			}
			if (fieldName.equals("projectInfo.clientName")) {
				if (!Validator.isClientName(newValue)) {
					return Logger.err("[%s] invalid client name", managerID);
				}
				((ManagerRecord)record).projectInfo.clientName = newValue;
				return Logger.log("[%s] updated project client's name", managerID);
			}
			if (fieldName.equals("projectInfo.projectName")) {
				if (!Validator.isFirstName(newValue)) {
					return Logger.err("[%s] invalid client name", managerID);
				}
				((ManagerRecord)record).projectInfo.projectName = newValue;
				return Logger.log("[%s] updated project name", managerID);
			}
		}

		return Logger.err("no editable field \"%s\" on record with ID \"%s\"", fieldName, recordID);
	}

	public String transferRecord(String managerID, String recordID, String remoteCenterServerName) {
		// TODO Auto-generated method stub
		return null;
	}
}
