package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class PlayingTask implements Runnable {

    private Player mPlayer;
    private String mInputPath;
    
    private int mPeriodMs;

    public PlayingTask(Player player, String outputPath, int period) {
        mPlayer = player;
        mPeriodMs = period;
        mInputPath = outputPath;
    }

    @Override
    public void run() {
    	Queue<Frame> frames = loadFrames(mInputPath);
        long startTime = FlashUtil.millisInt();
        
        for(Frame frame : frames) {
        	mPlayer.play(frame);

            try {
                Thread.sleep(mPeriodMs - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
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
            e.printStackTrace();
        }
        
        return frames;
    }
}
