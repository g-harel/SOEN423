package server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import common.Logger;

public class Server {
	
	private static int port = 1099;

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			Logger.log("missing location");
			return;
		}
		String location = args[0];
		// TODO check location is valid.
		
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
