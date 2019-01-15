package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
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
    	Queue<Frame> frames = loadFrames(mInputPath);
        long startTime = FlashUtil.millisInt();
        
        if(frames != null) {
        	for(Frame frame : frames) {
            	mPlayer.play(frame);

                try {
                    Thread.sleep(mPeriodMs - (startTime - FlashUtil.millisInt()));
                    startTime = FlashUtil.millisInt();
                } catch (InterruptedException e) {
                	if(mLogger.equals(null))
                		System.out.println(e.toString());
                	else
                		mLogger.warning(e.toString());   
                    break;
                }
            }
        }
    }

    private Queue<Frame> loadFrames(String path) {
        Queue<Frame> frames = new ArrayDeque<Frame>();
        
        try(BufferedReader file = new BufferedReader(new FileReader(path))) {
        	for(String line = file.readLine(); line != null; line = file.readLine()) {
            	frames.add(new Frame(line));
            }
        } catch (IOException e) {
        	if(mLogger.equals(null))
        		System.out.println(e.toString());
        	else
        		mLogger.warning(e.toString());   
        	PlayingRunner.stop();
        	return null;
        }
        return frames;
    }
}
