package webservice;

import location.Logger;
import location.Validator;
import location.Location;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import location.AddressBook;
import location.InteractiveClient;

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

		String locationCode = managerID.substring(0, 2);
		AddressBook ab = new AddressBook(locationCode);

		URL url;
		try {
			url = new URL(ab.selfAddr());
		} catch (MalformedURLException e) {
			Logger.err("%s", e);
			return;
		}
        QName qname = new QName("http://webservice/", "LocationWSService");
        Service service = Service.create(url, qname);

		QName qport = new QName("http://webservice/","LocationWSPort");
        ILocationWS location = service.getPort(qport, ILocationWS.class);

		while (InteractiveClient.start(location, managerID)) {}
	}
}
