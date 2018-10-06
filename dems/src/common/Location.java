package common;

public class Location {
	
	private static String[] validLocations = new String[]{"CA", "US", "UK"};
	
	public static String[] list() {
		String[] copy = new String[Location.validLocations.length];
		System.arraycopy(Location.validLocations, 0, copy, 0, Location.validLocations.length);
		return copy;
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
	
	public String toString() {
		return this.value;
	}
	
}
	