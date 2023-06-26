package com.rivera.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Automatically Generating Success
 * We can create immediate success. This is required if you wish to compose a
 * successful CompletableFuture with another. Notice that we are using a method
 * called handle, which will handle either the success or failure in the form of
 * a BiFunction. A BiFunction takes two arguments. In this example, the first is
 * the value if sucessful, while the second argument is the exception if it was
 * unsuccessful.
 * 
 * The following example will add 10 to the value of 10 already provided:
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

public class Main3 {
    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Return a completable future that is already completed with the given value
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.completedFuture(10);

        CompletableFuture<Integer> handle = integerCompletableFuture.handle((i, e) -> {
            if (i != null) {
                return i + 10;
            } else {
                return 0;
            }
        });

        handle.thenAccept(System.out::println);

        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
