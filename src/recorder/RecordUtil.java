package recorder;

import java.io.File;

class RecordUtil {
	public static void ensureIsDirectory(File folder) {    	
    	if(!(folder.exists() && folder.isDirectory()))
    		throw new IllegalArgumentException("The entered directory does not exist");
    }
}
