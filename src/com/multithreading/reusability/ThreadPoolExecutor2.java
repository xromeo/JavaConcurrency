package com.multithreading.reusability;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Work Queues for ThreadPools
 */

public class ThreadPoolExecutor2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                2,
                1,
                TimeUnit.MINUTES,
                // new ArrayBlockingQueue<>(3)
                // new LinkedBlockingQueue<>()
                new SynchronousQueue<>());

        threadPoolExecutor.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Task 1");

        threadPoolExecutor.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Task 2");

        threadPoolExecutor.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Task 3");

        // rejected from java.util.concurrent.ThreadPoolExecutor@4aa298b7[Running, pool
        // size = 2, active threads = 2, queued tasks = 0, completed tasks = 0]

    }
}
