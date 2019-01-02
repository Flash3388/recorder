package recorder;

public class PlayingRunner{

	private Player[] mPlayers;
	private String mFilename;
	private Executer mExec;
	
    public PlayingRunner(String filename,Player... players) {
        this.mPlayers = players;
        this.mFilename = filename;
        
        mExec = new Executer();
    }

    public void multiPlay() {
        for (Player actor : mPlayers) {
            String path = RecordingRunner.FILE_PATH+ mFilename + "-" + actor.getName() + ".rec";
            PlayingTask scriptReader = new PlayingTask(actor,path);
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
