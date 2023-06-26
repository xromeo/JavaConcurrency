package com.advancements;

/*
 * Completing a CompletableFuture Asynchronously
When creating a CompletableFuture as an incomplete CompletableFuture, the only way to provide a value was complete. Now there is completeAsync, which can be run from a different Executor:
 * 
 *  */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {
    public static void main(String[] args)
        throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Integer> completableFuture =
            new CompletableFuture<>();
        completableFuture.thenAccept(System.out::println);
        Thread.sleep(1000);

        completableFuture.completeAsync(() -> 10, executorService);

        Thread.sleep(1100);
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
