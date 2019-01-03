package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class RecordingTask implements Runnable {

    private String mPath;
    private int mPeriod;
    
    private Recorder mRecorder;
    private Queue<Frame> mFrames;

    public RecordingTask(Recorder recorder, String path, int period) {
        mPath = path;
        mRecorder = recorder;
        mPeriod = period;
        mFrames = new ArrayDeque<Frame>();
    }

    @Override
    public void run() {
        long startTime = FlashUtil.millisInt();

        while(!Thread.interrupted()) {
        	mFrames.add(mRecorder.capture());
            try {
                Thread.sleep(mPeriod - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();

            } catch (InterruptedException e) {
                break;
            }
        }
        saveFrames(mPath, mFrames);
    }

    private void saveFrames(String path, Queue<Frame> frames) {
        try {
            FileWriter writer = new FileWriter(path);
            try {
                for(Frame frame : frames) {
                    writer.write(frame.getData());
                    writer.write("\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
