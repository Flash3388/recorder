package execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor {
	private final ExecutorService executorService;
	private final List<Future<?>> futures;
    
    public Executor() {
    	executorService = Executors.newCachedThreadPool();
        futures = new ArrayList<>();
    }
    
    public void submit(Runnable task) {
    	futures.add(executorService.submit(task));
    }
    
    public boolean isFinished() {
        return futures.stream()
                .allMatch(Future::isDone);
    }

    public void stop() {
        futures.stream()
                .filter(future -> !future.isDone() || future.isCancelled())
                .forEach(future -> future.cancel(true));
        futures.clear();
    }
    
    public void shutdown() {
    	executorService.shutdown();
    	stop();
    }
}
