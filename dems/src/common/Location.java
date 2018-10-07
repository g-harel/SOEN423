package common;

public class Location {
	
	private static String[] validLocations = new String[] {"CA", "US", "UK"};
	
	public static Location[] list() {
		Location[] locations = new Location[Location.validLocations.length];
		for (int i = 0; i < Location.validLocations.length; i++) {
			locations[i] = new Location(Location.validLocations[i]);
		}
		return locations;
	}
	
	//
	
	public String value;
	
	public Location(String location) throws LocationError {
		for (String l : Location.validLocations) {
			if (location.equals(l)) {
				this.value = location;
				return;
			}
		}
		
		throw new LocationError(String.format(
			"invalid location, must be one of \"%s\" but got \"%s\"",
			String.join(", ", Location.validLocations),
			location
		));
	}
	
	public Boolean equals(Location l) {
		return this.value.equals(l.value);
	}
	
	public String serverName() {
		return "rmi://localhost/" + this.value;
	}
	
	public String toString() {
		return this.value;
	}
	
}
	