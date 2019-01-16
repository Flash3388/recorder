package recorder;

import java.io.File;
import java.util.logging.Logger;

public class PlayingRunner{	
	private Executor mExecutor;
	private Logger mLogger;
	
	private Player[] mPlayers;
	private int mPeriodMs;
	
	/**
	 * 
	 * @param logger is the logger where the errors would be logged
	 * @param period Has to be equal to the period set in Recorder for the autonomous to work
	 * @param players I don't like this
	 * @param Hi Tom, writing doc sucks!
	 * @param executor executor for the threads
	 */
    public PlayingRunner(Logger logger, Executor executor, int period, Player... players) {    
        mPlayers = players;
        mPeriodMs = period;
        mLogger = logger;
        mExecutor = executor;
    }
    /**
     * @param inputFolder - filename should not include the ".rec"
     */
    public void play(File inputFolder) {
    	RecordUtil.ensureIsDirectory(inputFolder);
    		for (Player actor : mPlayers) {
                String path = String.format("%s/%s.rec", inputFolder.getPath(),actor.getName());
                PlayingTask scriptReader = new PlayingTask(actor, path, mPeriodMs, mLogger);
                mExecutor.submit(scriptReader);
            }
    }
    
    public boolean isFinished() {
    	return mExecutor.isFinished();
    }
    
    public void stop() {
    	mExecutor.stop();
    }
}
