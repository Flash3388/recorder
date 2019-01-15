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
    
    public RecordingRunner(int periodMs, Recorder... recorders) {
    	this(null,new Executor(),periodMs,recorders);
    }
    
    public RecordingRunner(Logger logger,int periodMs, Recorder... recorders) {
    	this(logger,new Executor(),periodMs,recorders);
    }
    
    public RecordingRunner(Executor executor,int periodMs, Recorder... recorders) {
    	this(null,executor,periodMs,recorders);
    }

    public void record(File outputFolder) {
    	if(RecordUtil.checkDir(outputFolder) && isFinished()) {
    		for(Recorder recorder : mRecorders) {
                String path = String.format("%s%s.rec", outputFolder.getPath(),recorder.getName());
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
