package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
	private static BufferedWriter writer;
	
	public static void file(String name) throws Exception {
		new File("./log").mkdirs();
		String filename = "./log/" + name + ".log";
		Logger.writer = new BufferedWriter(new FileWriter(filename));
	}
	
	public static String log(String format, Object... args) throws Exception {
		String str = String.format(format, args);
		String msg = String.format("%tY/%<tb/%<te %<tT - %s%n", LocalDateTime.now(), str);
		
		if (Logger.writer != null) {
			Logger.writer.write(msg);
			Logger.writer.flush();
		}
		System.out.print(msg);
		
		return str;
	}
}
