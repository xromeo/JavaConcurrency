package com.advancements;

/*
 * Copying a CompletableFuture
There is a copy method on CompletableFuture that came out in JDK 9. It relays the result of the original CompletableFuture, but it is a CompletableFuture onto its own and can be useful to prevent downstream clients from competing if they have their own defensive copies of the CompletableFuture:
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Integer> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        System.out.println("intFuture1 is Sleeping in thread: "
                                + Thread.currentThread().getName());
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 5;
                }, executorService);

        CompletableFuture<Integer> copy = completableFuture.copy();

        System.out.println(copy.isDone());
        System.out.println(copy.isDone());

        Thread.sleep(3100);
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
