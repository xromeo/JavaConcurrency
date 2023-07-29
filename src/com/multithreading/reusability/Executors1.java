package com.multithreading.reusability;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

// Creating Thread Pools with Executors

public class Executors1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService  executorService = Executors.newFixedThreadPool(5);
        ExecutorService  executorService1 = Executors.newCachedThreadPool(new MyThreadFactory());

        Future<?> future = executorService1.submit(() -> {
            System.out.println("Hola");
        });
        

        future.get();

        executorService1.shutdown();
        System.out.println();
    }

    static class MyThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);

            t.setPriority(4);
            t.setName("my-thread");

            return t;
        }


    }
        
}
