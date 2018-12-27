package recorder;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.util.FlashUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class RecordingTask extends Action implements Runnable {

    private String mPath;
    private Recorder mRecorder;
    private Queue<Frame> mFrames;

    public RecordingTask(Recorder recorder,String path) {
    	requires((Subsystem) mRecorder);
        mPath = path;
        mRecorder = recorder;
        mFrames = new ArrayDeque<Frame>();
    }

    @Override
    public void run() {
    	execute();
    }

	@Override
	protected void end() {
        toFile(mPath);
	}

	@Override
	protected void execute() {
		mFrames.add(mRecorder.capture());
	}
	

    private void toFile(String path) {
        try {
            FileWriter writer = new FileWriter(path);
            try {
                for(Frame frame : mFrames) {
                    writer.write(frame.getData());
                    writer.write("\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
        mFrames.clear();
    }
}
