package recorder;

import java.util.logging.Logger;

public class PlayingRunner{	
	private static Executor mExecutor;
	private Logger mLogger;
	
	private Player[] mPlayers;
	private int mPeriodMs;
	
	/**
	 * 
	 * @param folderPath The folder where the 
	 * @param period Has to be equal to the period set in Recorder for the autonomous to work
	 * @param players
	 */
    public PlayingRunner(Logger logger, int period, Player... players) {    
        mPlayers = players;
        mPeriodMs = period;
        mLogger = logger;
        mExecutor = new Executor();
    }

    public void play(String inputFolderPath) {
    	if(IDunnoHowToNameIT.isDir(inputFolderPath) && isFinished()) {
    		for (Player actor : mPlayers) {
                String path = String.format("%s%s.rec", inputFolderPath,actor.getName());
                PlayingTask scriptReader = new PlayingTask(actor, path, mPeriodMs, mLogger);
                mExecutor.submit(scriptReader);
            }
    		mExecutor.stop();
    	}
    }
    
    public boolean isFinished() {
    	return mExecutor.isFinished();
    }
    
    public static void stop() {
    	mExecutor.stop();
    }
}
