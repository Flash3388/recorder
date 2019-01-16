package recorder;

import java.io.File;
import java.util.logging.Logger;

public class RecordingRunner {
    private Recorder[] mRecorders;
    private Executor mExecutor;
    private Logger mLogger;
    
    private int mPeriodMs;
    
    public RecordingRunner(Logger logger, Executor executor, int periodMs, Recorder... recorders) {
    	if(periodMs <= 0 )
    		throw new IllegalArgumentException();        
    	mExecutor = executor;
    	
    	mRecorders = recorders;
    	mPeriodMs = periodMs;
    	mLogger = logger;
    }
    /**
     * @param outputFolder - filename should not include the ".rec"
     */
    public void record(File outputFolder) {
    	RecordUtil.ensureIsDirectory(outputFolder);
    	//if(isFinished()) - should I do that? had it before, but now I think that it's not that necessary.
		for(Recorder recorder : mRecorders) {
            String path = String.format("%s/%s.rec", outputFolder.getPath(),recorder.getName());
            RecordingTask scriptWriter = new RecordingTask(recorder, path, mPeriodMs, mLogger);
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
