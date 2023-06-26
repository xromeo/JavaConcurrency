package com.advancements;

/*
 * Delaying an Executor After a Certain Time
In JDK 9, one of the new enhancements is a delayed Executor that can be used to start a CompletableFuture after a certain time. This is a static factory method, and once we obtain this Executor, we can plug it into any call that allows us to plug in a different Executor.

In the following example, we are using the delayedExecutor from within CompletableFuture.supplyAsync because we want a delay of 10 seconds:
* 
 *  */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main3 {
    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Executor executor = CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS);
        CompletableFuture.supplyAsync(() -> 4, executor).thenAccept(System.out::println);
        Thread.sleep(10100);
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}