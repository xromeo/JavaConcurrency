package com.rivera.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Choosing Your Executor
 * When you create a CompletableFuture, you actually have a choice as to what
 * kind of thread from a thread pool you wish to use. These thread pools are
 * called Executors. The Executor can be placed in the submit call, usually as
 * the last parameter.
 * 
 * Keep in mind that most calls allow you to put whichever executor you'd like
 * to use to perform the operation; if you do not specify, typically you will
 * get a thread from a ForkJoin thread pool.
 * 
 * In this example, we procure a CompletableFuture with a thread from a fixed
 * thread pool. For the second CompletableFuture, we use the default ForkJoin
 * thread pool: *
 * We will then compile:
 * 
 * javac -d target src/com/rivera/Main.java
 * 
 * Then we will run the following:
 * 
 * java -cp target com.rivera.Main
 * 
 */

public class Main5 {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 4;
        }, executorService);
        integerCompletableFuture.thenAccept(System.out::println);

        // The following is going to use the default Thread Pool

        CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 4;
        });
        integerCompletableFuture2.thenAccept(System.out::println);

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
