package recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Executer {
	private ScheduledExecutorService mExecutorService;
	private List<Future<?>> mFutures;
    
    public Executer() {
    	mExecutorService = Executors.newSingleThreadScheduledExecutor();
        mFutures = new ArrayList<>();
    }
    
    public void submit(Runnable task) {
    	mFutures.add(mExecutorService.scheduleAtFixedRate(task, 0, 10, TimeUnit.MILLISECONDS));
    }
    
    public void submit(Runnable task, long period) {
    	mFutures.add(mExecutorService.scheduleAtFixedRate(task, 0, period, TimeUnit.MILLISECONDS));
    }
    
    public boolean isFinished() {
        for (Future<?> future : mFutures) {
            if (!future.isDone()) {
                return false;
            }
        }
        return true;
    }

    public void stop() {
        for (Future<?> future : mFutures) {
            if (!future.isDone() || future.isCancelled()) {
                future.cancel(true);
            }
        }
        mFutures.clear();
    }

}
