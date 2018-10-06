package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import common.Location;
import common.Logger;

public class Server {
	
	private static int port = 1099;

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			Logger.log("missing location argument");
			return;
		}
		Location location = new Location(args[0]);
		Logger.log("using location %s", location);
		
		Logger.file("server-" + location);
		
		try {
			LocateRegistry.createRegistry(Server.port);
			Logger.log("created registry on :%s", Server.port);
		} catch (RemoteException e) {			
			Logger.log("registry already exists");
		}
		
		new CenterServer(location);
	}
	
}
