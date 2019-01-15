package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayingTask implements Runnable {
    private String mInputPath;
    private Logger mLogger;
    
    private Player mPlayer;
    private int mPeriodMs;

    public PlayingTask(Player player, String outputPath, int period, Logger logger) {
        mPlayer = player;
        mPeriodMs = period;
        mInputPath = outputPath;
        mLogger = logger;
    }

    @Override
    public void run() {
    	Queue<Frame> frames = null;
        long startTime = FlashUtil.millisInt();
        
        try {
			frames = loadFrames(mInputPath);
		} catch (IOException e) {
        	mLogger.log(Level.SEVERE,String.format("Error accured while reading frames from %s", mPlayer.getName()),e);  
        }
        
        if(frames != null) {
        	for(Frame frame : frames) {
            	mPlayer.play(frame);

                try {
                    Thread.sleep(mPeriodMs - (startTime - FlashUtil.millisInt()));
                    startTime = FlashUtil.millisInt();
                } catch (InterruptedException e) {
                	mLogger.log(Level.WARNING,"The Playing thread got interrupted",e);
                }
            }
        }
    }

    private Queue<Frame> loadFrames(String path) throws FileNotFoundException, IOException {
        Queue<Frame> frames = new ArrayDeque<Frame>();
        
        try(BufferedReader file = new BufferedReader(new FileReader(path))) {
        	for(String line = file.readLine(); line != null; line = file.readLine()) {
            	frames.add(new Frame(line));
            }
        }
        
        return frames;
    }
}
