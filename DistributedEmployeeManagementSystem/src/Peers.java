import java.util.ArrayList;
import java.util.Arrays;

public class Peers {
	private static ArrayList<String> all = new ArrayList<String>(Arrays.asList(new String[] {"CA", "US", "UK"}));

	public static String pattern() {
		return "(" + String.join("|", Peers.all) + ")";
	}

	///

	private ArrayList<String> peers;

	public Peers(String selfLocationCode) {
		this.peers = new ArrayList<String>();
		for (String locationCode : Peers.all) {
			if (!selfLocationCode.equals(locationCode)) {
				this.peers.add(locationCode);
			}
		}
	}
}
