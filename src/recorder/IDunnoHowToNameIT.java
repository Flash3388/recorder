package recorder;

import java.io.File;

class IDunnoHowToNameIT {
	public static boolean isDir(String folderPath) {
    	File folder = new File(folderPath);
    	
    	if(folder.exists()) {
    		if(folder.isDirectory())
    			return true;
    		else
    			throw new IllegalArgumentException("The entered directory is not a directory");
    	} else
    		throw new IllegalAccessError("The entered directory does not exist");
    }
}
