package server;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.EmployeeRecord;
import common.Location;
import common.Logger;
import common.ManagerRecord;
import common.Project;
import common.Record;
import common.CenterServerInterface;

public class CenterServer extends UnicastRemoteObject implements CenterServerInterface {

	private HashMap<Character, ArrayList<Record>> records;
	private HashMap<String, CenterServerInterface> servers;
	private Location location;

	public CenterServer(Location location) throws IOException {
		super(0);

		this.records = new HashMap<Character, ArrayList<Record>>();
		this.servers = new HashMap<String, CenterServerInterface>();
		this.location = location;

		Naming.rebind(location.serverName(), this);
		Logger.log("server bound to name: \"%s\"", location.serverName());
		
		this.tryToConnect();
	}
	
	private synchronized void tryToConnect() throws IOException {
		for (Location l : Location.list()) {
			if (this.location.equals(l)) {
				continue;
			}
			if (this.servers.containsKey(l.toString())) {
				continue;
			}
			try {
				this.servers.put(l.toString(), (CenterServerInterface)Naming.lookup(l.serverName()));
				Logger.log("successfully connected to \"%s\" server", l);
			} catch (NotBoundException e) {
				Logger.log("attempted and failed to connect to \"%s\" server", l);
			}
		}
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

	public synchronized Boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project) throws IOException {
		ManagerRecord record = new ManagerRecord(firstName, lastName, employeeID, mailID, project, this.location.toString());
		Character index = lastName.toUpperCase().charAt(0);
		ArrayList<Record> records = null;
		if (!this.records.containsKey(lastName.charAt(0))) {
			records = new ArrayList<Record>();
			this.records.put(index, records);
		} else {
			records = this.records.get(index);
		}
		records.add(record);
		Logger.log("manager record created for \"%s %s\"", firstName, lastName);
		return true;
	}

	public synchronized Boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID, String location) throws IOException {
		Location l = new Location(location);
		if (!l.equals(this.location)) {
			this.tryToConnect();
			if (!this.servers.containsKey(l.toString())) {
				Logger.log("cannot create employee record in \"%s\" server", l);
				return false;
			} else {
				CenterServerInterface remoteServer = this.servers.get(l.toString());
				Logger.log("forwarding employee record creation request to \"%s\" server", location);
				return remoteServer.createERecord(firstName, lastName, employeeID, mailID, projectID, location);
			}
		}
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
		Logger.log("employee record created for \"%s %s\"", firstName, lastName);
		return true;
	}
	
	public synchronized String getSelfRecordCounts() {
		return String.format("%s %d", this.location, this.records.size());
	}

	public synchronized String getRecordCounts() throws IOException {
		this.tryToConnect();
		String res = "";
		for (CenterServerInterface value : this.servers.values()) {
		    res += value.getSelfRecordCounts() + ", ";
		}
		return res + this.getSelfRecordCounts();
	}

	public synchronized String editRecord(String recordID, String fieldName, String newValue) throws IOException {
		Record record = this.getRecord(recordID);
		if (record == null) {
			return Logger.log("ERROR: no record with that ID found");
		}

		if (fieldName == "mailID") {
			if (newValue == null || newValue.equals("")) {
				return Logger.log("ERROR: empty mailID");
			}
			record.mailID = newValue;
			return Logger.log("updated mailID");
		}

		if (recordID.substring(0, 2) == "ER") {
			if (fieldName == "projectID") {
				((EmployeeRecord)record).projectID = newValue;
				return Logger.log("updated projectID");
			}
		}

		if (recordID.substring(0, 2) == "MR") {
			if (fieldName == "location") {
				if (newValue.length() == 2 && "CA, US, UK".indexOf(newValue) != -1) {
					return Logger.log("ERROR: invalid location");
				}
				((ManagerRecord)record).location = newValue;
				return Logger.log("updated location");
			}
			if (fieldName == "projectInfo") {
				// TODO
			}
		}

		return Logger.log("ERROR: operation not found");
	}

}
