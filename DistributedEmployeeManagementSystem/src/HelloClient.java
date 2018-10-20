import HelloApp.*;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class HelloClient {
    static Hello helloImpl;

    public static void main(String args[]) throws Exception {
        ORB orb = ORB.init(args, null);

        // Get reference to naming service.
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Create implementation from named reference.
        String name = "Hello";
        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

        System.out.println(helloImpl.sayHello());
    }
}
