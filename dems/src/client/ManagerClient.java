package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import common.CenterServerInterface;
import common.Logger;

public class ManagerClient {
	
	public static void main(String[] args) throws NotBoundException, IOException {
		if (args.length < 1) {
			Logger.log("missing location");
			return;
		}
		
		ManagerClient client = new ManagerClient(args[0]);
		Logger.log(client.server.getRecordCounts());
	}

	public CenterServerInterface server;
	
	public ManagerClient(String location) throws MalformedURLException, RemoteException, NotBoundException {
		this.server = (CenterServerInterface)Naming.lookup("//localhost/" + location);
	}

}
