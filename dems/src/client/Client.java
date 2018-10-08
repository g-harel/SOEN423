package client;

import java.io.IOException;
import java.rmi.NotBoundException;

import common.Location;
import common.Logger;
import common.Project;

public class Client {
	
	public static void main(String[] args) throws NotBoundException, IOException {
		if (args.length < 1) {
			Logger.log("missing manager id");
			return;
		}
		String managerID = args[0];
		if (!managerID.matches("[A-Z]{2}\\d{4}")) {
			Logger.log("invalid manager id \"%s\"", managerID);
			return;
		}
		
		Location location = new Location(managerID.substring(0, 2));
		
		Logger.file("client-" + managerID);
		
		Logger.log("connecting with manager id \"%s\" to location \"%s\"", managerID, location);
		
		ManagerClient client = new ManagerClient(location);
		
		while (true) {			
			client.listen();
		}
	}
}
