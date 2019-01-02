package recorder;

public class RecordingRunner {

	public static final String FILE_PATH = "/home/lvuser/recorder/";
	public static final int PERIOD = 10;
	
    private Recorder[] mRecorders;
    private Executer mExec;

    private String mPath;
    
    public RecordingRunner(String path, Recorder... recorders) {
        if(contains(path, '/'))
        	mPath = path;
        else
        	mPath = FILE_PATH + path;
    	
        this.mRecorders = recorders;
        
        mExec = new Executer();
    }
    
    public boolean contains(String str, char chr) {
    	  return str.indexOf(chr) != -1;
    	}

    public void multiRecord() {
        for(Recorder script : mRecorders) {
            System.out.println("recording actor");
            String path = mPath+"-"+script.getName()+".rec";
            RecordingTask scriptWriter = new RecordingTask(script, path);
            mExec.submit(scriptWriter);
        }
    }
    
    public boolean isFinished() {
    	return mExec.isFinished();
    }
    
    public void stop() {
    	mExec.stop();
    }
}
