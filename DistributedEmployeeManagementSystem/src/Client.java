import DEMS.*;
import common.Logger;
import common.Validator;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Client {
    public static void main(String args[]) throws Exception {
    	if (args.length < 1) {
			Logger.log("error, no manager ID provided");
			System.exit(1);
    	}
    	
    	String managerID = args[0];
    	if (!Validator.isManagerID(managerID)) {
			Logger.log("error, invalid manager ID \"%s\"", managerID);
			System.exit(1);
    	}

		Logger.file("client-" + managerID);
    	
        ORB orb = ORB.init(args, null);

        // Get reference to naming service.
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Create implementation from named reference.
    	String locationCode = managerID.substring(0, 2);
        Location locationImpl = LocationHelper.narrow(ncRef.resolve_str(locationCode));
        
        Logger.log(locationImpl.address());
    }
}
