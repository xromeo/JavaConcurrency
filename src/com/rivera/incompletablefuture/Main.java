package com.rivera.incompletablefuture;
/**
 * In this Katacoda, we discover how we can create an incompletable Future. With CompletableFutures, we can instantiate a plain CompletableFuture that would be answered manually by some other process. In Java, this is called incomplete, but you may have heard it called promise. Up until this point, most of the CompletableFutures that we have been using have been eager to run, immediately setting off to perform work. Now we will manually provide answers when complete.
 */

 import java.util.concurrent.*;
 /*
  * Creating a Incomplete Future
To create an incomplete Future, we instantiate CompletableFuture with no parameters. It is as simple as that. When we feel like we wish to complete the future, regardless of thread, we can call complete.

In the following example, notice that the thenAccept is in the middle of code and complete is further down the application. thenAccept is just waiting until the answer arrives:
  */

public class Main {
    public static void main(String[] args)
        throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> completableFuture =
            new CompletableFuture<>();

        completableFuture.thenAccept(System.out::println);

        System.out.println("Processing something else");
        Thread.sleep(1000);
        completableFuture.complete(42); //force a complete

        Thread.sleep(1100);
        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
