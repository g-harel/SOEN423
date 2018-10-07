package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import common.CenterServerInterface;
import common.Location;
import common.Logger;

public class ManagerClient {

	public CenterServerInterface server;

	public ManagerClient(Location location) throws NotBoundException, IOException {
		this.server = (CenterServerInterface)Naming.lookup(location.serverName());
		Logger.log("found \"%s\" server", location);
	}

}
