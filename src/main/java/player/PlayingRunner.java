package player;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import execution.Executor;
import util.RecordUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PlayingRunner{	
	private final Executor executor;
	private final Logger logger;
	
	private final List<Player> players;

	private final Clock clock;
	private final Time timeStep;

	public PlayingRunner(Logger logger, Executor executor, Clock clock, Time timeStep, Player... players) {
	    this(logger, executor, clock, timeStep, Arrays.asList(players));
    }

    public PlayingRunner(Logger logger, Executor executor, Clock clock, Time timeStep, List<Player> players) {
        this.players = players;
        this.timeStep = timeStep;
        this.logger = logger;
        this.executor = executor;
        this.clock = clock;
    }

    public void play(File inputFolder) {
    	RecordUtil.ensureIsDirectory(inputFolder);
    	players.forEach(player -> executor.submit(createTask(inputFolder, player)));
    }

    private PlayingTask createTask(File inputFolder, Player player) {
        String path = String.format("%s/%s.rec", inputFolder.getPath(), player.getName());
        PlayingTask task = new PlayingTask(path, logger, clock, player, timeStep);

        return task;
    }
    
    public boolean isFinished() {
    	return executor.isFinished();
    }
    
    public void stop() {
    	executor.stop();
    }
}
