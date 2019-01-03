package recorder;

public class PlayingRunner{

	private Player[] mPlayers;
	private Flashexecutor mExecutor;
	private String mOutputPath;
	private int mPeriodMs;
	
	public PlayingRunner(String outputPath, Player... players) {
        mPlayers = players;
        mOutputPath = outputPath;
        mPeriodMs = RecordingRunner.DEFAULT_PERIOD_MS;
        
        mExecutor = new Flashexecutor();
    }
	
    public PlayingRunner(String outputPath, int period, Player... players) {
        mPlayers = players;
        mOutputPath = outputPath;
        mPeriodMs = period; // The period here and in Recorder must be the same to achieve accurate results
        
        mExecutor = new Flashexecutor();
    }

    public void multiPlay() {
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
