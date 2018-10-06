package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.EmployeeRecord;
import common.ManagerRecord;
import common.Project;
import common.Record;
import common.CenterServerInterface;

public class CenterServer extends UnicastRemoteObject implements CenterServerInterface {

	private static final long serialVersionUID = 1L;
	private HashMap<Character, ArrayList<Record>> records;
	private String location;
	
	public CenterServer(String location) throws MalformedURLException, RemoteException {
		super(0);
		
		this.records = new HashMap<Character, ArrayList<Record>>();
		this.location = location;
		
		String name = "rmi://localhost/" + location;
		Naming.rebind(name, this);
		System.out.println("server bound to name: " + name);
	}
	
	private Record getRecord(String recordID) {
		for (Map.Entry<Character, ArrayList<Record>> records : this.records.entrySet()) {
			for (Record record : records.getValue()) {
				if (record.recordID == recordID) {
					return record;
				}
			}
		}
		return null;
	}
	
	// CenterServerInterface implementation

	public Boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project) throws RemoteException {
		ManagerRecord record = new ManagerRecord(firstName, lastName, employeeID, mailID, project, this.location);
		Character index = lastName.toUpperCase().charAt(0);
		ArrayList<Record> records = null;
		if (!this.records.containsKey(lastName.charAt(0))) {
			records = new ArrayList<Record>();
			this.records.put(index, records);
		} else {
			records = this.records.get(index);
		}
		records.add(record);
		return true;
	}

	public Boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID, String location) throws RemoteException {
		// TODO validate / do something with location
		EmployeeRecord record = new EmployeeRecord(firstName, lastName, employeeID, mailID, projectID);
		Character index = lastName.toUpperCase().charAt(0);
		ArrayList<Record> records = null;
		if (!this.records.containsKey(lastName.charAt(0))) {
			records = new ArrayList<Record>();
			this.records.put(index, records);
		} else {
			records = this.records.get(index);
		}
		records.add(record);
		return true;
	}
	
	public String getRecordCounts() {
		// TODO talk to other servers ??
		return "test count";
	}
	
	public String editRecord(String recordID, String fieldName, String newValue) {
		Record record = this.getRecord(recordID);
		if (record == null) {
			return "ERROR: no record with that ID found";
		}
		
		if (fieldName == "mailID") {
			if (newValue == null || newValue.equals("")) {
				return "ERROR: empty mailID";
			}
			record.mailID = newValue;
			return "updated mailID";
		}
		
		if (recordID.substring(0, 2) == "ER") {
			if (fieldName == "projectID") {
				((EmployeeRecord)record).projectID = newValue;
				return "updated projectID";
			}
		}
		
		if (recordID.substring(0, 2) == "MR") {
			if (fieldName == "location") {
				if (newValue.length() == 2 && "CA, US, UK".indexOf(newValue) != -1) {
					return "ERROR: invalid location";
				}
				((ManagerRecord)record).location = newValue;
				return "updated location";
			}
			if (fieldName == "projectInfo") {
				// TODO
			}
		}
		
		return "ERROR: operation not found";
	}

}
