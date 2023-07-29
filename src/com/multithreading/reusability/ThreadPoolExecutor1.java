package com.multithreading.reusability;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                5,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(3));

        // threadPoolExecutor.execute(() -> System.out.println("Task 1"));
        // Future<Integer> future = threadPoolExecutor.submit(new CallableTask());

        // // Do other stuff

        // System.out.println(future.isDone());
        // System.out.println(future.get());

        threadPoolExecutor.execute(() -> System.out.println("Task 1"));
        threadPoolExecutor.execute(() -> System.out.println("Task 2"));
        threadPoolExecutor.execute(() -> System.out.println("Task 3"));
        threadPoolExecutor.execute(() -> System.out.println("Task 4"));
        threadPoolExecutor.execute(() -> System.out.println("Task 5"));
        threadPoolExecutor.execute(() -> System.out.println("Task 6"));

        System.out.println("Pool size: " + threadPoolExecutor.getPoolSize());

        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS);
    }

    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return 4;
        }

    }

}
