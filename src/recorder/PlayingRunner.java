package recorder;

public class PlayingRunner{

	private Player[] mPlayers;
	private String mFilename;
	private Executer mExec;
	private int mPeriod;
	
	public PlayingRunner(String filename, Player... players) {
        mPlayers = players;
        mFilename = filename;
        mPeriod = RecordingRunner.PERIOD;
        
        mExec = new Executer();
    }
	
    public PlayingRunner(String filename, int period, Player... players) {
        mPlayers = players;
        mFilename = filename;
        mPeriod = period; // The period here and in Recorder must be the same to achieve accurate results
        
        mExec = new Executer();
    }

    public void multiPlay() {
        for (Player actor : mPlayers) {
            String path = RecordingRunner.FILE_PATH+ mFilename + "-" + actor.getName() + ".rec";
            PlayingTask scriptReader = new PlayingTask(actor, path, mPeriod);
            mExec.submit(scriptReader);
        }
    }
    
    public boolean isFinished() {
    	return mExec.isFinished();
    }
    
    public void stop() {
    	mExec.stop();
    }
}
