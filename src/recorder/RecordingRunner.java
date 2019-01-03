package recorder;

public class RecordingRunner {

	public static final String FILE_PATH = "/home/lvuser/recorder/";
	private final int DEFAULT_PERIOD_MS = 10;
	
    private Recorder[] mRecorders;
    private Flashexecutor mExecutor;

    private String mOutputPath;
    private int mPeriodMs;
    
    public RecordingRunner(String fullPath, Recorder... recorders) {
        mOutputPath = fullPath;
        mRecorders = recorders;
        mExecutor = new Flashexecutor();
        mPeriodMs = DEFAULT_PERIOD_MS;
    }
    
    public RecordingRunner(String fullPath, int periodMs, Recorder... recorders) {
    	this(fullPath,recorders);
    	if(periodMs <= 0 )
    		throw new IllegalArgumentException();        
       
    	mPeriodMs = periodMs;
    }

    public void record() {
        for(Recorder script : mRecorders) {
            String path = String.format("%s-%s.rec", mOutputPath,script.getName());
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
