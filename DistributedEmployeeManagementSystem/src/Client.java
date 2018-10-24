import EmployeeManagementSystem.*;

import org.omg.CosNaming.*;

import org.omg.CORBA.*;

public class Client {
    public static void main(String args[]) {
    	if (args.length < 1 || args[0].equals("")) {
			Logger.err("no manager ID provided");
			System.exit(1);
    	}

    	String managerID = args[0];
    	if (!Validator.isManagerID(managerID)) {
			Logger.err("invalid manager ID \"%s\"", managerID);
			System.exit(1);
    	}

		Logger.writeTo("client-" + managerID);
		Logger.log("starting as \"%s\"", managerID);

		Prompt.forValue("name");

		try {
	        ORB orb = ORB.init(args, null);

	        // Get reference to naming service.
	        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

	        // Create implementation from named reference.
	    	String locationCode = managerID.substring(0, 2);

			Location locationImpl = LocationHelper.narrow(ncRef.resolve_str(locationCode));

	        Logger.log(locationImpl.getRecordCounts(managerID));
		} catch (Exception e) {
			Logger.err("%s", e);
		}
    }
}
