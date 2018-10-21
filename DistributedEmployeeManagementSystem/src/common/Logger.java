package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
	private static BufferedWriter writer;
	
	public static void file(String name) {
		new File("./log").mkdirs();
		String filename = "./log/" + name + ".log";
		try {			
			Logger.writer = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			System.out.println("could not open log file: " + e);
		}
	}
	
	public static String log(String format, Object... args) {
		String str = String.format(format, args);
		String msg = String.format("%tY/%<tb/%<te %<tT - %s%n", LocalDateTime.now(), str);
		
		if (Logger.writer != null) {
			try {
				Logger.writer.write(msg);
				Logger.writer.flush();
			} catch (Exception e) {
				System.out.println("could not write logs to file: " + e);
			}
		}
		System.out.print(msg);
		
		return str;
	}
}
