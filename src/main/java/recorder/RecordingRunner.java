package recorder;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import execution.Executor;
import util.RecordUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class RecordingRunner {
    private final List<Recorder> recorders;
    private final Executor executor;
    private final Logger logger;

    private final Clock clock;
    private final Time timeStep;

    public RecordingRunner(Logger logger, Executor executor, Clock clock, Time timeStep, Recorder... recorders) {
        this(logger, executor, clock, timeStep, Arrays.asList(recorders));
    }

    public RecordingRunner(Logger logger, Executor executor, Clock clock, Time timeStep, List<Recorder> recorders) {
        this.recorders = recorders;
        this.executor = executor;
        this.logger = logger;

    	this.timeStep = timeStep;
        this.clock = clock;
    }

    public void record(File outputFolder) {
    	RecordUtil.ensureIsDirectory(outputFolder);
		recorders.forEach(recorder -> executor.submit(generateTask(outputFolder, recorder)));
    }

    private RecordingTask generateTask(File outputFolder, Recorder recorder) {
        String path = String.format("%s/%s.rec", outputFolder.getPath(),recorder.getName());
        RecordingTask task = new RecordingTask(recorder, clock, path, timeStep, logger);

        return task;
    }
    
    public boolean isFinished() {
    	return executor.isFinished();
    }
    
    public void stop() {
    	executor.stop();
    }
}
