package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Scanner;

import common.CenterServerInterface;
import common.Location;
import common.Logger;
import common.Project;

public class ManagerClient {

	private CenterServerInterface remote;

	public ManagerClient(Location location) throws NotBoundException, IOException {
		this.remote = new LoggingClient((CenterServerInterface)Naming.lookup(location.serverName()));

		Logger.log("found \"%s\" server", location);
	}

	public void listen() throws IOException {
		Scanner scanner = new Scanner (System.in);
		System.out.println("enter a command:");
		String command = scanner.nextLine();

		Logger.log("started command \"%s\"", command);

		if (command.equals("help")) {
			System.out.println("  help:         print command descriptions");
			System.out.println("  count:        prints the number of stored records in all running servers");
			System.out.println("  mngr:         creates a new manager record for the current server");
			System.out.println("  empl-ca:      creates a new employee in the CA server");
			System.out.println("  empl-us:      creates a new employee in the US server");
			System.out.println("  empl-uk:      creates a new employee in the UK server");
			System.out.println("  edit-mngr:    edits the clientName in a manager's project");
			System.out.println("  edit-empl:    edits a local employee's mailID");
			System.out.println("  exit:         exit tool");
		} else if (command.equals("count")) {
			this.remote.getRecordCounts();
		} else if (command.equals("mngr")) {
			this.remote.createMRecord("Alex", "Smith", 54321, "alexsmith@example.com", new Project("P0000", "Jane Lee", "Project 0000"));
		} else if (command.equals("empl-ca")) {
			this.remote.createERecord("John", "Doe", 12345, "johndoe@example.com", "P0000", "CA");
		} else if (command.equals("empl-us")) {
			this.remote.createERecord("John", "Doe", 12345, "johndoe@example.com", "P0000", "US");
		} else if (command.equals("empl-uk")) {
			this.remote.createERecord("John", "Doe", 12345, "johndoe@example.com", "P0000", "UK");
		} else if (command.equals("edit-mngr")) {
			this.remote.editRecord("MR00000", "projectInfo.clientName", "Charles David");
		} else if (command.equals("edit-empl")) {
			this.remote.editRecord("ER00000", "mailID", "noreply@example.com");
		} else if (command.equals("exit")) {
			Logger.log("exiting");
			System.exit(0);
		} else {
			Logger.log("command \"%s\" is not recognized (use \"help\" for list of available commands)", command);
		}
	}

}
