package common;

public abstract class Validator {
	public static boolean isLocationCode(String str) {
		return str.matches("^(CA|US|UK)$");
	}
	
	public static boolean isManagerID(String str) {
		return str.matches("^MR\\d{5}$");
	}
	
	public static boolean isEmployeeID(String str) {
		return str.matches("^ER\\d{5}$");
	}
	
	public static boolean isEmail(String str) {
		return str.matches("^[^\\s]+@[^\\s]+\\.[^\\s]{2,}$");
	}
	
	public static boolean isFirstName(String str) {
		return str.matches("\\w+");
	}
	
	public static boolean isLastName(String str) {
		return str.matches("\\w+");
	}
	
	public static boolean isProjectID(String str) {
		return str.matches("^P\\d{5}$");
	}
	
	public static boolean isProjectName(String str) {
		return str.matches("\\w+");
	}
}
