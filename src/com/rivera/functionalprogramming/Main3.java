package com.rivera.functionalprogramming;

/**
Perform a Runnable Action at Termination
After our CompletableFuture is complete, we can run a Runnable instance using thenRun. If you're wondering what the difference between thenAccept and thenRun is, thenAccept accepts the results of our chain, but thenRun does not accept any parameter. It is solely an action that runs at the end of our CompletableFuture chain.

If you wish for thenRun to run on a separate thread, keep in mind there is also a thenRunAsync:
*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main3 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);
                CompletableFuture<Integer> integerFuture1 = CompletableFuture
                                .supplyAsync(() -> 5, executorService);
                integerFuture1.thenRun(() -> {
                        String successMessage = "I am doing something else once" +
                                        " that future has been triggered!";
                        System.out.println(successMessage);
                        System.out.println("Run inside of " +
                                        Thread.currentThread().getName());
                });
                Thread.sleep(100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}
