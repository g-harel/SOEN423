package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	private static BufferedWriter writer;
	
	public static void file(String name) throws IOException {
		new File("./log").mkdirs();
		String filename = "./log/" + name + ".log";
		Logger.writer = new BufferedWriter(new FileWriter(filename));
	}
	
	public static String log(String format, Object... args) throws IOException {
		String str = String.format(format + "\n", args);
		
		if (Logger.writer != null) {
			Logger.writer.write(str);
			Logger.writer.flush();
		}
		System.out.print(str);
		
		return str;
	}
	
}
