package client;

import java.util.Scanner;

import common.Logger;

public class Prompt {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String forValue(String valueName) {
		System.out.println(valueName + ": ");
		String value = Prompt.scanner.nextLine();
		Logger.log(valueName + ": " + value);
		return value;
	}
}
