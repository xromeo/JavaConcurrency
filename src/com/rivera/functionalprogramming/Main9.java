package com.rivera.functionalprogramming;

/**
Waiting for Any CompletableFutures to Complete
This time, we use anyOf when we have two or more independent CompletableFutures. Whichever finishes first will be enough to continue. Using the same CompletableFutures, one is taking 3 seconds to finish, and the other is taking 400 milliseconds. As soon as the 400 milliseconds is complete, it will pass through the anyOf:
*/

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main9 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);

                CompletableFuture<Integer> integerFuture1 = CompletableFuture
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

                CompletableFuture<Integer> integerFuture2 = CompletableFuture
                                .supplyAsync(() -> {
                                        try {
                                                System.out.println("intFuture2 is sleeping in thread: "
                                                                + Thread.currentThread().getName());
                                                Thread.sleep(400);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        return 555;
                                });

                long start = System.currentTimeMillis();
                CompletableFuture.anyOf(integerFuture1, integerFuture2).join();
                long end = System.currentTimeMillis();
                System.out.println(
                                "Guaranteed that all futures have completed in: "
                                                + (end - start));
                Thread.sleep(410);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}