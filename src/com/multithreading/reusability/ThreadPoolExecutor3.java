package com.multithreading.reusability;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Handling Exceptions in ThreadPools
 */

public class ThreadPoolExecutor3 {

    public static void main(String[] args){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                2,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2));

        Future<?> future = threadPoolExecutor.submit(() -> {
            throw new RuntimeException(" Runtime exception test");
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  catch (ExecutionException e) {
            e.printStackTrace();
        }

        

    }
}
