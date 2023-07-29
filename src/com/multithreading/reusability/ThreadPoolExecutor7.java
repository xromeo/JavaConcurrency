package com.multithreading.reusability;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Monitoring the Performance of a ThreadPool

public class ThreadPoolExecutor7 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                3,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(5),
                (Runnable r, ThreadPoolExecutor executor) -> {
                    // Put some metrics
                });

        // Returns the current number of threads in the pool.
        threadPoolExecutor.getPoolSize();

        // Returns the approximate number of threads that are actively executing tasks.
        threadPoolExecutor.getActiveCount();

        // Returns the approximate total number of tasks that have ever been scheduled
        // for execution
        threadPoolExecutor.getTaskCount();

        // Returns the approximate total number of tasks that have completed execution
        threadPoolExecutor.getCompletedTaskCount();

    }

}
