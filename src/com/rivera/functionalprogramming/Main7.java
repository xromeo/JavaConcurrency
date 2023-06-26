package com.rivera.functionalprogramming;

/**
Combining CompletableFuture with thenCombine
thenCompose is perfect for carrying the result of one CompletableFuture into another and then squashing the nested CompletableFuture into one CompletableFuture. Sometimes, though, we don't need to carry the result from one to another, we just want to bring the results of two CompletableFutures or more into one CompletableFuture. This is where thenCombine serves that particular purpose.

In the following example, integerFuture1 and integerFuture2 run independently of each other, as opposed to the previous step using thenCompose, where the result of one is required by the next. For these independent CompletableFutures, we use thenCombine.

Note that there is also a thenCombineAsync that accepts an Executor, in case you require the combining to take place on a different thread. Also, take a look at acceptBoth and acceptBothAsync, which operate the same as thenCombine, but as a terminal operation:
*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main7 {
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

                CompletableFuture<Integer> combine = integerFuture1
                                .thenCombine(
                                                integerFuture2, (x, y) -> {
                                                        System.out.println(
                                                                        "Inside f:" + Thread.currentThread().getName());
                                                        return x + y;
                                                });

                combine.thenAccept(System.out::println);
                Thread.sleep(4100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}