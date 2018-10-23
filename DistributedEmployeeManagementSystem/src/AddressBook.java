import java.util.ArrayList;

class LocationEntry {
	public String locationCode;
	public int port;
	
	public LocationEntry(String locationCode, int port) {
		this.locationCode = locationCode;
		this.port = port;
	}
}

public class AddressBook {
	private static LocationEntry[] all = new LocationEntry[] {
		new LocationEntry("CA", 3331),
		new LocationEntry("US", 3332),
		new LocationEntry("UK", 3333),
	};

	public static String locationPattern() {
		if (AddressBook.all.length == 0) {
			return "";
		}
		String res = "(" + AddressBook.all[0].locationCode;
		for (int i = 1; i < AddressBook.all.length; i++) {
			res += "|" + AddressBook.all[i].locationCode;
		}
		return res + ")";
	}

	///

	private ArrayList<LocationEntry> peers;
	private LocationEntry self;

	public AddressBook(String selfLocationCode) {
		this.peers = new ArrayList<LocationEntry>();
		for (LocationEntry location : AddressBook.all) {
			if (!selfLocationCode.equals(location.locationCode)) {
				this.peers.add(location);
			} else {
				this.self = location;
			}
		}
	}
	
	public String selfName() {
		return this.self.locationCode;
	}
	
	public int selfPort() {
		return this.self.port;
	}
	
	public String[] names() {
		String[] res = new String[this.peers.size()];
		int i = 0;
		for (LocationEntry location : this.peers) {
			res[i] = location.locationCode;
			i++;
		}
		return res;
	}
	
	
	public int[] ports() {
		int[] res = new int[this.peers.size()];
		int i = 0;
		for (LocationEntry location : this.peers) {
			res[i] = location.port;
			i++;
		}
		return res;
	}
}
