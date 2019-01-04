package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class RecordingTask implements Runnable {

    private String mOutputPath;
    private int mPeriodMs;
    
    private Recorder mRecorder;

    public RecordingTask(Recorder recorder, String outputPath, int period) {
        mOutputPath = outputPath;
        mRecorder = recorder;
        mPeriodMs = period;
    }

    @Override
    public void run() {
    	Queue<Frame> frames = new ArrayDeque<Frame>();
    	long startTime = FlashUtil.millisInt();

        
        while(!Thread.interrupted()) {
        	frames.add(mRecorder.capture());
            try {
                Thread.sleep(mPeriodMs - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();

            } catch (InterruptedException e) {
                break;
            }
        }
        saveFrames(mOutputPath, frames);
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
