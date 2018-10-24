import EmployeeManagementSystem.*;

import org.omg.CosNaming.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class Server {
	public static void main(String args[]) {
		if (args.length < 1) {
			Logger.err("no location code provided");
			System.exit(1);
		}

		String locationCode = args[0];
		if (!Validator.isLocationCode(locationCode)) {
			Logger.err("invalid location code '%s'", locationCode);
			System.exit(1);
		}

		Logger.writeTo("server-" + locationCode);

		RecordServer rs = new RecordServer(new AddressBook(locationCode));

		try {
			ORB orb = ORB.init(args, null);

			// Get reference to naming service.
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// Create implementation instance.
			LocationImpl locationImpl = new LocationImpl(rs);
			locationImpl.setORB(orb);

			// Create reference to implementation.
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			org.omg.CORBA.Object ref = rootPOA.servant_to_reference(locationImpl);
			Location sref = LocationHelper.narrow(ref);

			// Bind reference to a name in naming service.
			NameComponent path[] = ncRef.to_name(locationCode);
			ncRef.rebind(path, sref);

			Logger.log("starting as '%s'", locationCode);

			Thread t = new Thread(rs);
			t.start();

			orb.run();
		} catch (Exception e) {
			Logger.err("%s", e);
		}
	}
}
