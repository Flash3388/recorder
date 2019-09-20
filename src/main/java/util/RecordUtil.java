package util;

import java.io.File;

public class RecordUtil {
	public static void ensureIsDirectory(File folder) {    	
    	if(!(folder.exists() && folder.isDirectory()))
    		throw new IllegalArgumentException("The entered directory does not exist");
    }
}
