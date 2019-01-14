package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;

public class RecordingTask implements Runnable {

    private String mOutputPath;
    private Logger mLogger;
    
    private int mPeriodMs;
    private Recorder mRecorder;

    public RecordingTask(Recorder recorder, String outputPath, int period, Logger logger) {
        mOutputPath = outputPath;
        mRecorder = recorder;
        mPeriodMs = period;
        mLogger = logger;
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
    	FileWriter writer;
    	
    	try {
    		writer = new FileWriter(path);
            try {
                for(Frame frame : frames) {
                    writer.write(frame.getData());
                    writer.write("\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
        	if(mLogger.equals(null))
        		System.out.println(e.toString());
        	else
        		mLogger.warning(e.toString());
        	File file = new File(path);
        	file.delete();
        }
    }
}
