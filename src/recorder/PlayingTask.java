package recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class PlayingTask implements Runnable {

    private Player mPlayer;
    private Queue<Frame> mFrames;

    public PlayingTask(Player player, String path) {
        this.mPlayer = player;
        mFrames = new ArrayDeque<Frame>();
        readScript(path);
    }

    @Override
    public void run() {
        long startTime = FlashUtil.millisInt();
        for(Frame frame : mFrames) {
        	mPlayer.play(frame);

            try {
                Thread.sleep(RecordingRunner.PERIOD - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void readScript(String path) {
        mFrames.clear();
        
        try(BufferedReader file = new BufferedReader(new FileReader(path))) {
        	for(String line = file.readLine(); line != null; line = file.readLine()) {
            	mFrames.add(new Frame(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
