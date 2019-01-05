package recorder;

import java.util.logging.Logger;

public class RecordingRunner {
    private Recorder[] mRecorders;
    private Executor mExecutor;
    private Logger mLogger;
    
    private int mPeriodMs;
    
    public RecordingRunner(Logger logger, int periodMs, Recorder... recorders) {
    	if(periodMs <= 0 )
    		throw new IllegalArgumentException();        
    	mExecutor = new Executor();
    	
    	mRecorders = recorders;
    	mPeriodMs = periodMs;
    	mLogger = logger;
    }

    public void record(String outputFolderPath) {
    	if(IDunnoHowToNameIT.isDir(outputFolderPath) && isFinished()) {
    		for(Recorder recorder : mRecorders) {
                String path = String.format("%s%s.rec", outputFolderPath,recorder.getName());
                RecordingTask scriptWriter = new RecordingTask(recorder, path, mPeriodMs, mLogger);
                mExecutor.submit(scriptWriter);
            }
    		stop();
    	}
    }
    
    public boolean isFinished() {
    	return mExecutor.isFinished();
    }
    
    public void stop() {
    	mExecutor.stop();
    }
}
