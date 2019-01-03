package recorder;

public class RecordingRunner {

	public static final String FILE_PATH = "/home/lvuser/recorder/";
	public static final int DEFAULT_PERIOD_MS = 10;
	
    private Recorder[] mRecorders;
    private Flashexecutor mExecutor;

    private String mPath;
    private int mPeriodMs;
    
    public RecordingRunner(String path, Recorder... recorders) {
        mPath = path;
        mRecorders = recorders;
        mExecutor = new Flashexecutor();
        mPeriodMs = DEFAULT_PERIOD_MS;
    }
    
    public RecordingRunner(String path, int periodMs, Recorder... recorders) {
        mPath = path;
        mRecorders = recorders;
        
        mExecutor = new Flashexecutor();
        if(periodMs <= 0 )
        	throw new IllegalArgumentException();        
        mPeriodMs = periodMs;
    }

    public void multiRecord() {
        for(Recorder script : mRecorders) {
            String path = String.format("%s-%s.rec", mPath,script.getName());
            RecordingTask scriptWriter = new RecordingTask(script, path, mPeriodMs);
            mExecutor.submit(scriptWriter);
        }
    }
    
    public boolean isFinished() {
    	return mExecutor.isFinished();
    }
    
    public void stop() {
    	mExecutor.stop();
    }
}
