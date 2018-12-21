package recorder;

public class RecordingRunner {

	public static final String FILE_PATH = "/home/lvuser/recorder/";
	public static final int PERIOD = 10;
	
    private Recorder[] mRecorders;
    private Executer mExec;

    private String mFilename;

    public RecordingRunner(String filename, Recorder... recorders) {
        this.mFilename = filename;
        this.mRecorders = recorders;
        
        mExec = new Executer();
    }

    public void multiRecord() {
        for(Recorder script : mRecorders) {
            System.out.println("recording actor");
            String path = FILE_PATH+mFilename+"-"+script.getClass().getSimpleName()+".rec";
            RecordingTask scriptWriter = new RecordingTask(script, path);
            mExec.submit(scriptWriter);
        }
    }
    
    public boolean isFinished() {
    	return mExec.isFinished();
    }
    
    public void stop() {
    	mExec.stop();
    }
}
