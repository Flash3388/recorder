package recorder;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import execution.Frame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordingTask implements Runnable {

    private final String outputPath;
    private final Logger logger;
    private final Clock clock;

    private final Recorder recorder;
    private final Time timeStep;

    public RecordingTask(Recorder recorder, Clock clock, String outputPath, Time timeStep, Logger logger) {
        this.outputPath = outputPath;
        this.recorder = recorder;
        this.timeStep = timeStep;
        this.logger = logger;
        this.clock = clock;
    }

    @Override
    public void run() {
    	Queue<Frame> frames = new ArrayDeque<>();
        Time startTime = clock.currentTime();
        
        while(!Thread.interrupted()) {
        	frames.add(recorder.capture());
            try {
                Thread.sleep(timeStep.sub(startTime.sub(clock.currentTime())).valueAsMillis());
                startTime = clock.currentTime();

            } catch (InterruptedException e) {
                break;
            }
        }
        saveFrames(outputPath, frames);
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
        	logger.log(Level.SEVERE,String.format("Error while saving frames in %s",path),e);
        	File file = new File(path);
        	file.delete();
        }
    }
}
