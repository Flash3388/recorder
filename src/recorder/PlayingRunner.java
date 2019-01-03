package recorder;

public class PlayingRunner{
	private final int DEFAULT_PERIOD_MS = 10;
	
	private Flashexecutor mExecutor;
	private String mOutputPath;
	
	private Player[] mPlayers;
	private int mPeriodMs;
	
	public PlayingRunner(String outputPath, Player... players) {
        mPlayers = players;
        mOutputPath = outputPath;
        mPeriodMs = DEFAULT_PERIOD_MS;
        
        mExecutor = new Flashexecutor();
    }
	
    public PlayingRunner(String outputPath, int period, Player... players) {
        this(outputPath,players);
        mPeriodMs = period; // The period here and in Recorder must be the same to achieve accurate results
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
