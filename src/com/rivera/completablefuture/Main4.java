package com.rivera.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
Automatically Generating Failure
Of course, with every success, there should be a failure. For that, we can use Completable.failedFuture with the argument of a Throwable:
 * 
 * We will then compile:
 * 
 * javac -d target src/com/rivera/Main.java
 * 
 * Then we will run the following:
 * 
 * java -cp target com.rivera.Main
 * 
 */

public class Main4 {
    public static void main(String[] args)
        throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> failedFuture =
            CompletableFuture.failedFuture(new Throwable("Unable to complete"));

        CompletableFuture<Integer> handle =
            failedFuture.handle((i, e) -> {
            if (i != null) {
                return i + 10;
            } else {
                return 0;
            }
        });

        handle.thenAccept(System.out::println);
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
