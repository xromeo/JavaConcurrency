package com.multithreading.reusability;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Managing Rejected Tasks in a ThreadPool

public class ThreadPoolExecutor5 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                3,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2));

        threadPoolExecutor.submit(new SleepingTask(1));
        threadPoolExecutor.submit(new SleepingTask(2));
        // Filled the  the pools size

        System.out.println("[1] Thread pool size : " + threadPoolExecutor.getPoolSize());

        threadPoolExecutor.submit(new SleepingTask(3));
        threadPoolExecutor.submit(new SleepingTask(4));
        // Filled the queue

        System.out.println("[2] Thread pool size : " + threadPoolExecutor.getPoolSize());
        
        threadPoolExecutor.submit(new SleepingTask(5));
        // Reach the maximum pool size
        
        System.out.println("[3] Thread pool size : " + threadPoolExecutor.getPoolSize());
        
        threadPoolExecutor.submit(new SleepingTask(6)); // <- Try catch is needed 
        //rejected from java.util.concurrent.ThreadPoolExecutor@42a57993[Running, pool size = 3, active threads = 3, queued tasks = 2, completed tasks = 0]
    }

    static class SleepingTask implements Runnable{

        private final int id;

        public SleepingTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
