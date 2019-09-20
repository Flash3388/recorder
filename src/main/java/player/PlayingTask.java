package player;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import execution.Frame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayingTask implements Runnable {
    private final String inputPath;
    private final Logger logger;
    private final Clock clock;
    
    private final Player player;
    private final Time timeStep;

    public PlayingTask(String inputPath, Logger logger, Clock clock, Player player, Time timeStep) {
        this.inputPath = inputPath;
        this.logger = logger;
        this.clock = clock;
        this.player = player;
        this.timeStep = timeStep;
    }

    @Override
    public void run() {
    	try {
    		Queue<Frame> frames = loadFrames(inputPath);
            Time startTime = clock.currentTime();
            
        	for(Frame frame : frames) {
            	player.play(frame);
                    Thread.sleep(timeStep.sub(startTime.sub(clock.currentTime())).valueAsMillis());
                    startTime = clock.currentTime();
            } 	
        } catch (InterruptedException e) {
        	logger.log(Level.WARNING,"The Playing thread got interrupted", e);
    	} catch (IOException e) {
        	logger.log(Level.SEVERE,String.format("Error accured while reading frames from %s", inputPath), e);
    	}
    }

    private Queue<Frame> loadFrames(String path) throws IOException {
        Queue<Frame> frames = new ArrayDeque<Frame>();
        
        try(BufferedReader file = new BufferedReader(new FileReader(path))) {
        	for(String line = file.readLine(); line != null; line = file.readLine()) {
            	frames.add(new Frame(line));
            }
        }     
        return frames;
    }
}
