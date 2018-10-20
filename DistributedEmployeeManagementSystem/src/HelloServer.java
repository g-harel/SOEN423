import HelloApp.*;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class HelloImpl extends HelloPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    // implement sayHello() method
    public String sayHello() {
        return "\nHello world !!\n";
    }

    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }
}


public class HelloServer {
    public static void main(String args[]) throws Exception {
        ORB orb = ORB.init(args, null);

        // Get reference to naming service.
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Create implementation instance.
        HelloImpl helloImpl = new HelloImpl();
        helloImpl.setORB(orb);

        // Create reference to implementation.
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();
        org.omg.CORBA.Object ref = rootPOA.servant_to_reference(helloImpl);
        Hello href = HelloHelper.narrow(ref);

        // Bind reference to a name in naming service.
        String name = "Hello";
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, href);

        System.out.println("HelloServer ready and waiting ...");

        orb.run();
    }
}
