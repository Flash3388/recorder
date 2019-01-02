package recorder;

public class RecordingRunner {

	public static final String FILE_PATH = "/home/lvuser/recorder/";
	public static final int PERIOD = 10;
	
    private Recorder[] mRecorders;
    private Executer mExec;

    private String mPath;
    private int mPeriod;
    
    public RecordingRunner(String path, Recorder... recorders) {
        if(contains(path, '/'))
        	mPath = path;
        else
        	mPath = FILE_PATH + path;
    	
        this.mRecorders = recorders;
        
        mExec = new Executer();
        mPeriod = PERIOD;
    }
    
    public RecordingRunner(String path, int period, Recorder... recorders) {
        if(contains(path, '/'))
        	mPath = path;
        else
        	mPath = FILE_PATH + path;
    	
        this.mRecorders = recorders;
        
        mExec = new Executer();
        if(period <= 0 )
        	throw new IllegalArgumentException();
        mPeriod = period;
    }
    
    public boolean contains(String str, char chr) {
    	  return str.indexOf(chr) != -1;
    	}

    public void multiRecord() {
        for(Recorder script : mRecorders) {
            System.out.println("recording actor");
            String path = mPath+"-"+script.getName()+".rec";
            RecordingTask scriptWriter = new RecordingTask(script, path, mPeriod);
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
