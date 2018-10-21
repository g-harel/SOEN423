import DEMS.*;
import common.Logger;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Client {
    public static void main(String args[]) throws Exception {
    	// TODO check manager ID
    	
        ORB orb = ORB.init(args, null);

        // Get reference to naming service.
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Create implementation from named reference.
        String name = "Hello";
        Location locationImpl = LocationHelper.narrow(ncRef.resolve_str(name));
        
        Logger.log(locationImpl.address());
    }
}
