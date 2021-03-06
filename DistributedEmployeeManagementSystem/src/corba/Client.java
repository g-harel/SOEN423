package corba;

import corba.EmployeeManagementSystem.*;
import location.InteractiveClient;
import location.Logger;
import location.Validator;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CORBA.*;

public class Client {
	public static void main(String args[]) {
		if (args.length < 1 || args[0].equals("")) {
			Logger.err("no manager ID provided");
			System.exit(1);
		}

		String managerID = args[0];
		if (!Validator.isManagerID(managerID)) {
			Logger.err("invalid manager ID '%s'", managerID);
			System.exit(1);
		}

		Logger.writeTo("client-" + managerID);
		Logger.log("starting as '%s'", managerID);

		ORB orb = ORB.init(args, null);

		Location locationImpl;
		try {
			// Get reference to naming service.
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// Create implementation from named reference.
			String locationCode = managerID.substring(0, 2);

			try {
				locationImpl = LocationHelper.narrow(ncRef.resolve_str(locationCode));
			} catch (NotFound e) {
				Logger.err("could not resolve reference to '%s' location", locationCode);
				return;
			}
		} catch (Exception e) {
			Logger.err("could not start client: %s", e);
			return;
		}

		while (InteractiveClient.start(new LocationRef(locationImpl), managerID)) {}
	}
}
