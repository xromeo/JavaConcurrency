package com.advancements;

/*
 * It's important that asynchronous processes do not last forever, and they should be limited with a timeout. This is where orTimeout can be useful. Attach orTimeout from anywhere in your chain of operations where you expect a timely response. If the operation takes more time than required, a TimeoutException is thrown. Lucky for us, in the CompletableFuture API, there is an exceptionally method that is able to catch the Throwable so that we can return a rescue value.

In this example, we intentionally set the CompletableFuture to run for 10 seconds, but we appended an orTimeout with 4 seconds. So we must complete within 4 seconds, but that will fail since there isn't anything we can do about the 10 seconds. The result is -1:* 
 *  */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main4 {
    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 30;
        });

        completableFuture
                .orTimeout(4, TimeUnit.SECONDS)
                .exceptionally(t -> -1)
                .thenAccept(System.out::println);

        Thread.sleep(4100);
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}