package com.rivera.completablefuture;
/**
 * 
In this Katacoda, we will explore the CompletableFuture, a new abstraction over the java.util.concurrent.Future you may have seen in our previous lab. There are a few advantages to the CompleteableFuture:

It is composable—we can compose the CompletableFutures as chains.
Composibility is done with function-style programming.
You choose whether each functional link is synchronous or asynchronous.
This Katacoda will focus on the creation of just the CompletableFuture. Be sure to look for the next one in this series, Java Concurrent Programming: Functional Programming a CompletableFuture, for a full demonstration of how to do functional programming with the CompletableFuture.
 */
import java.util.concurrent.*;
/**
 * Creating a Basic Future
The following is a standard way to set up a java.util.concurrent.Future. We create a java.util.concurrent.Callable with what you wish to run on a separate process. When we want to create a Future, we require a thread pool. Some available thread pools include:

FixedThreadPool
CachedThreadPool
SingleThreadPool
The default thread is what is called ForkJoinThreadPool, which is also used when you perform parallelism in Java Stream.

In the following example, notice that we are calling get(). The get() call blocks, so while we are invoking the Callable on another thread, we are waiting for it. This is like hiring a contractor to help you fix your house because you don't have time, and then you waste that time watching them:


Creating a Basic CompletableFuture
Here is a short and sweet example of a CompletableFuture. We're using a very common factory method, supplyAsync, which takes a Supplier, a type of lambda that accepts no arguments, and returns an object—in this case, an Integer. This is why our CompletableFuture is a CompletableFuture<Integer>. Just like the regular Future, it starts immediately, and we can receive the results of the CompletableFuture with the thenAccept method:


We will then compile:

javac -d target src/com/rivera/Main.java

Then we will run the following:

java -cp target com.rivera.Main
 * 
 */

public class Main {
        public static void main(String[] args)
        throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture
            .supplyAsync(() -> {
                try {
                    System.out.println("Sleeping in thread: "
                        + Thread.currentThread().getName());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 5;
            }, executorService);

        integerCompletableFuture.thenAccept(System.out::println);

        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
