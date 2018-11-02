package webservice;

import location.Logger;
import location.Validator;
import location.RecordServer;
import location.Location;
import location.AddressBook;

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

		Location location = new Location(rs);
	}
}
