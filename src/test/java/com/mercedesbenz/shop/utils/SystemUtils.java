package com.mercedesbenz.shop.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SystemUtils {

	public static void writeToFile(String fileName, String message, boolean appendMessages) {

		PrintWriter printWriter = null;
		File file = new File(fileName);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			printWriter = new PrintWriter(new FileOutputStream(fileName, appendMessages));
			printWriter.write(message + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}

	}

}
