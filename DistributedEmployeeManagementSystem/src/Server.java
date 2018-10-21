import DEMS.*;
import server.LocationImpl;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class Server {
    public static void main(String args[]) throws Exception {
        ORB orb = ORB.init(args, null);

        // Get reference to naming service.
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Create implementation instance.
        LocationImpl locationImpl = new LocationImpl();
        locationImpl.setORB(orb);

        // Create reference to implementation.
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();
        org.omg.CORBA.Object ref = rootPOA.servant_to_reference(locationImpl);
        Location sref = LocationHelper.narrow(ref);

        // Bind reference to a name in naming service.
        String name = "Hello";
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, sref);

        System.out.println("HelloServer ready and waiting ...");

        orb.run();
    }
}
