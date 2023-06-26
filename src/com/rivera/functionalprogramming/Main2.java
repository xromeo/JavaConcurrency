package com.rivera.functionalprogramming;

/**
Performing an Asynchronous Transformation
With each function that we apply to our chain, or stage, we can choose to use the Async version of the operation. For example, if we want to use the thenApply but on a different thread, we can call the thenApplyAsync. Later we will use thenCompose, and that too has an Async alternative. Of course, thenAccept has an asynchronous alternative: thenAcceptAsync.

In the following example, after creating the CompletableFuture we use thenApplyAsync, which performs the same operation as the previous step except on a different thread. Finally, we then chain at the end a thenAcceptAsync, which also runs on a different thread.

Last thing to notice: thenApplyAsync has two signatures, one that accepts an ExecutorService and another that doesn't. If an ExecutorService is not provided, you will be provided with a thread from the default Fork-Join pool instead. Therefore, in the following snippet, thenApply will use a Fork-Join thread and thenAcceptAsync will use a thread from the fixed thread pool that was declared immediately after the main method declaration:
*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);
                CompletableFuture<Integer> integerFuture1 = CompletableFuture
                                .supplyAsync(() -> 5, executorService);
                integerFuture1.thenApplyAsync(x1 -> {
                        System.out.println("In Block:" + Thread.currentThread().getName());
                        return "" + (x1 + 19);
                }).thenAcceptAsync((x) -> {
                        System.out.println("Accepting in:" + Thread.currentThread().getName());
                        System.out.println("x = " + x);
                }, executorService);
                System.out.println("Main:" + Thread.currentThread().getName());
                Thread.sleep(100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}
