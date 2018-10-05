package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import common.CenterServerInterface;

public class ManagerClient {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		if (args.length < 1) {
			System.out.println("missing location");
			return;
		}
		
		ManagerClient client = new ManagerClient(args[0]);
		System.out.println(client.server.getRecordCounts());
	}

	public CenterServerInterface server;
	
	public ManagerClient(String location) throws MalformedURLException, RemoteException, NotBoundException {
		this.server = (CenterServerInterface)Naming.lookup("//localhost/" + location);
	}

}
