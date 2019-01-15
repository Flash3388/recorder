package recorder;

import java.io.File;

class RecordUtil {
	public static boolean checkDir(File folder) {    	
    	if(folder.exists() && folder.isDirectory())
    			return true;
    	else
    		throw new IllegalAccessError("The entered directory does not exist");
    }
}
