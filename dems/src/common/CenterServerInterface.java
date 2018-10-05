package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CenterServerInterface extends Remote {
	// public Boolean createMRecord(String firstName, String lastName, int employeeID, String mailID, Project project) throws RemoteException;
	// public Boolean createERecord(String firstName, String lastName, int employeeID, String mailID, String projectID, String location) throws RemoteException;
	public String getRecordCounts() throws RemoteException;
	// public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException;
}
