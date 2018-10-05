package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import common.EmployeeRecord;
import common.ManagerRecord;
import common.CenterServerInterface;
import common.Records;

public class CenterServer extends UnicastRemoteObject implements CenterServerInterface {
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		if (args.length < 1) {
			System.out.println("missing location");
			return;
		}
		
    	LocateRegistry.createRegistry(1099); 
    	new CenterServer(args[0]);
	}

	private static final long serialVersionUID = 1L;
	
	public Records<EmployeeRecord> employees;
	public Records<ManagerRecord> managers;
	
	public CenterServer(String location) throws MalformedURLException, RemoteException {
		super(0);
		Naming.rebind("//localhost/" + location, this);
	}
	
	public String getRecordCounts() {
		return "test count";
	}

}
