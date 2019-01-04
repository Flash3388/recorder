package recorder;

public class PlayingRunner{
	private final static int DEFAULT_PERIOD_MS = 10;
	
	private Executor mExecutor;
	private String mOutputPath;
	
	private Player[] mPlayers;
	private int mPeriodMs;
	
	public PlayingRunner(String outputPath, Player... players) {
		this(outputPath,DEFAULT_PERIOD_MS,players);
    }
	
	/**
	 * 
	 * @param outputPath
	 * @param period Has to be equal to the period set in Recorder for the autonomous to work
	 * @param players
	 */
    public PlayingRunner(String outputPath, int period, Player... players) {    
        mPlayers = players;
        mOutputPath = outputPath;
        mPeriodMs = period;
        mExecutor = new Executor();
    }

    public void play() {
        for (Player actor : mPlayers) {
            String path = String.format("%s-%s.rec", mOutputPath,actor.getName());
            PlayingTask scriptReader = new PlayingTask(actor, path, mPeriodMs);
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
