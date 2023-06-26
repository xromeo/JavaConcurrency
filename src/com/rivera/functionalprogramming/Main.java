package com.rivera.functionalprogramming;

/**
 * Performing a Transformation with thenApply
You may have encountered thenAccept if you went through the one of the previous labs. If you haven't, thenAccept accepts the result of the chain, and performs a side effect. A side effect is a function that returns no result because it is doing something outside of our local environment. This can be printing to the screen, updating a state, or sending over the network.

I would also like for you to meet thenApply. thenApply is analogous to map. Whatever the answer is from the Future, you can manipulate it with thenApply.

In the following example, the original CompletableFuture returns a 5. We then apply a function, x -> String.valueOf(x + 19), so that we add 19 and convert it to a String. Finally, we use thenAccept to perform a side effect, printing to the screen:
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> integerFuture1 = CompletableFuture
                .supplyAsync(() -> 5, executorService);

        integerFuture1.thenApply(x -> String.valueOf(x + 19))
                .thenAccept(System.out::println);

        Thread.sleep(100);
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
