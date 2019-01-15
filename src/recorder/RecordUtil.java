package recorder;

import java.io.File;

class RecordUtil {
	public static boolean ensureIsDirectory(File folder) {    	
    	if(folder.exists() && folder.isDirectory())
    			return true;
    	else
    		throw new IllegalArgumentException("The entered directory does not exist");
    }
}
