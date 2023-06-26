package com.rivera.functionalprogramming;

/**
 * Handling Exceptions with Exceptionally
We can handle any Exception that may arise in our CompletableFuture with exceptionally. exceptionally takes a Function where the argument is the Throwable that just occurred. The end user must decide what to do with the Throwable and provide an alternate with the correct type. What is meant by the correct type? In the example below, we are running stringFuture1.thenApply(Integer::parseInt) where we first start with a CompletableFuture<String>, but after thenApply we expect that the type will be CompletableFuture<Integer> since we are parsing. If there is a Throwable that occurs, we still expect a CompletableFuture<Integer>, so we are choosing -1 as the alternate:
*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main4 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);
                CompletableFuture<String> stringFuture1 = CompletableFuture
                                .supplyAsync(() -> {
                                        try {
                                                System.out.println("stringFuture1 is sleeping in thread: "
                                                                + Thread.currentThread().getName());
                                                Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        return "Bloomington, MN";
                                }, executorService);

                stringFuture1.thenApply(Integer::parseInt)
                                .exceptionally(t -> -1)
                                .thenAccept(System.out::println);

                System.out.println("This message should appear first.");
                Thread.sleep(1100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}
