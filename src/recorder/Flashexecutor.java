package recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Flashexecutor {
	private ExecutorService mExecutorService;
	private List<Future<?>> mFutures;
    
    public Flashexecutor() {
    	mExecutorService = Executors.newSingleThreadExecutor();
        mFutures = new ArrayList<>();
    }
    
    public void submit(Runnable task) {
    	mFutures.add(mExecutorService.submit(task));
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
