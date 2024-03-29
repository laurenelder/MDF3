/* Name: Devin "Lauren" Elder
 * Date: 09/18/2014
 * Term: 1409
 * Project Name: Widget App
 * Assignment: MDF3 Week 3
 */

package com.laurenelder.widgetapp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

// FileManager reads and writes data to a files on the device
public class FileManager {

	private static FileManager manager_instance;
	String tag = "FILE MANAGER CLASS";

	// Constructor
	private FileManager() {

	}

	// Return instance method
	public static FileManager getInstance() {
		if (manager_instance == null) {
			manager_instance = new FileManager();
		}
		return manager_instance;
	}

	// Write contents to internal file
	public Boolean writeToFile(Context context, String fileName, String fileContent) {
		Boolean completion = false;
		FileOutputStream outputStream = null;

		try {
			outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			outputStream.write(fileContent.getBytes());
			completion = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(tag, e.getMessage().toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(tag, e.getMessage().toString());
			e.printStackTrace();
		}
		return completion;
	}

	/**
	 * Read from file.
	 *
	 * @param context the context
	 * @param fileName the file name
	 * @return the string
	 */

	// Read contents of internal file... Contents are the parsed in the MAINACTIVITY
	public String readFromFile(Context context, String fileName) {
		String fileContent = "";
		FileInputStream inputStream = null;

		try {
			inputStream = context.openFileInput(fileName);
			BufferedInputStream bufferInput = new BufferedInputStream(inputStream);
			byte[] cBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer cBuffer = new StringBuffer();
			while ((bytesRead = bufferInput.read(cBytes)) != -1) {
				fileContent = new String(cBytes, 0, bytesRead);
				cBuffer.append(fileContent);
			}
			fileContent = cBuffer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(tag, e.getMessage().toString());
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(tag, e.getMessage().toString());
				e.printStackTrace();
			}
		}
		return fileContent;
	}

}
