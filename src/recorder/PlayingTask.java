package recorder;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.Subsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class PlayingTask extends Action implements Runnable {

    private Player mPlayer;
    private Queue<Frame> mFrames;

    public PlayingTask(Player player, String path) {
        requires((Subsystem) mPlayer);
    	this.mPlayer = player;
        mFrames = new ArrayDeque<Frame>();
        readScript(path);
    }

	@Override
	protected void end() {}

	@Override
	protected void execute() {
		mPlayer.play(mFrames.poll());
	}

	@Override
	public void run() {
		execute();
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