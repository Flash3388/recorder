package recorder;

public class RecordingRunner {
    private Recorder[] mRecorders;
    private Executor mExecutor;

    private String mOutputPath;
    private int mPeriodMs;
    
    public RecordingRunner(String fullPath, int periodMs, Recorder... recorders) {
    	if(periodMs <= 0 )
    		throw new IllegalArgumentException();        
       
    	mOutputPath = fullPath;
        mRecorders = recorders;
    	mPeriodMs = periodMs;
    	
    	mExecutor = new Executor();
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
