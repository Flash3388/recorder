package recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor {
	private ExecutorService mExecutorService;
	private List<Future<?>> mFutures;
    /**
     * Executor is used to run Runnable threads with the ExecutorService
     */
    public Executor() {
    	mExecutorService = Executors.newCachedThreadPool();
        mFutures = new ArrayList<>();
    }
    /**
     * The method submits the Runnable task to the ExecutorService from Executor and adds it to the Futures List in Executor
     * 
     * @param task -Runnable thread to ran using the Executor (with the ExecutorService)
     */
    public void submit(Runnable task) {
    	mFutures.add(mExecutorService.submit(task));
    }
    /**
     * The function checks if there are still running futures in the Future List from Executor.
     * 
     * @return true if there are some futures running and false if not.
     */
    public boolean isFinished() {
        for (Future<?> future : mFutures) {
            if (!future.isDone()) {
                return false;
            }
        }
        return true;
    }
    /**
     * The method cancels all still running futures in the Future List from Executor and then clears it.
     */
    public void stop() {
        for (Future<?> future : mFutures) {
            if (!future.isDone() || future.isCancelled()) {
                future.cancel(true);
            }
        }
        mFutures.clear();
    }
    /**
     * The method calls for the shutdown() method from ExecutorService and then calls for the stop() method from Executor.
     */
    public void shutdown() {
    	mExecutorService.shutdown();
    	stop();
    }
}
