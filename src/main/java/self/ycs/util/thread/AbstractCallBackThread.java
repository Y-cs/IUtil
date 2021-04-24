package self.ycs.util.thread;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Y-cs
 * @date 2021/4/24 18:16
 */
public abstract class AbstractCallBackThread<T, R> {

    private final CountDownLatch countDownLatch;
    private Executor executor;
    private static final boolean USE_COMMON_POOL = (ForkJoinPool.getCommonPoolParallelism() > 1);
    private final Executor asyncPool = USE_COMMON_POOL ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();

    public AbstractCallBackThread(int taskSize)  throws InterruptedException {
        AbstractCallBackThread(null,taskSize,0,null);
    }

    public AbstractCallBackThread(Executor executor, int taskSize, long timeOut, TimeUnit timeUnit) throws InterruptedException {
        if (executor != null) {
            this.executor = executor;
        }
        this.countDownLatch = new CountDownLatch(taskSize);
        if (timeOut > 0) {
            boolean await = this.countDownLatch.await(timeOut, timeUnit);
        }
    }

    /**
     * handle
     *
     * @param t
     * @return
     */
    abstract R handle(T t);

    public void asyncTask(Supplier<T> supplier) {
        asyncPool.execute(() -> {
            T obj = supplier.get();
            handle(obj);
        });
    }

    static class ThreadPerTaskExecutor implements Executor {
        @Override
        public void execute(Runnable command) {

        }
    }

}
