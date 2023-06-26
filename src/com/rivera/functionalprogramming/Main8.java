package com.rivera.functionalprogramming;

/**
Waiting for All CompletableFutures to Complete
When we have two or more independent CompletableFutures, and we want to wait for all the CompletableFutures to complete before continuing, we can use allOf. In the following example, each CompletableFuture takes a certain amount of time; therefore, waiting for all the futures using allOf should be slightly higher than the combined sum time elapsed of the two CompletableFutures. Since one CompletableFuture will take 3 seconds and the other will take 400 milliseconds, we will wait for 3 seconds because that is the longest:

*/

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main8 {
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
                CompletableFuture.allOf(integerFuture1, integerFuture2).join();
                long end = System.currentTimeMillis();
                System.out.println(
                                "Guaranteed that all futures have completed in: "
                                                + (end - start));
                Thread.sleep(6100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}