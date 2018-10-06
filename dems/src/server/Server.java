package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
	
	private static int port = 1099;

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		if (args.length < 1) {
			System.out.println("missing location");
			return;
		}
		String location = args[0];
		// TODO check location is valid.
		
		try {
			LocateRegistry.createRegistry(Server.port);
			System.out.println("created registry on :" + Server.port);
		} catch (RemoteException e) {			
			System.out.println("registry already exists");
		}
		
		new CenterServer(location);
	}
	
}
