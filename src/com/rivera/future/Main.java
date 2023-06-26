package com.rivera.future;

import java.util.concurrent.*;
/**
 * Creating a Basic Future
The following is a standard way to set up a java.util.concurrent.Future. We create a java.util.concurrent.Callable with what you wish to run on a separate process. When we want to create a Future, we require a thread pool. Some available thread pools include:

FixedThreadPool
CachedThreadPool
SingleThreadPool
The default thread is what is called ForkJoinThreadPool, which is also used when you perform parallelism in Java Stream.

In the following example, notice that we are calling get(). The get() call blocks, so while we are invoking the Callable on another thread, we are waiting for it. This is like hiring a contractor to help you fix your house because you don't have time, and then you waste that time watching them:

We will then compile:

javac -d target src/com/rivera/Main.java

Then we will run the following:

java -cp target com.rivera.Main
 * 
 */

public class Main {
    public static void main(String[] args) 
     throws ExecutionException, InterruptedException {
        ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Inside the future: " +
                        Thread.currentThread().getName());
                System.out.println("Future priority: "
                        + Thread.currentThread().getPriority());
                Thread.sleep(5000);
                return 5 + 3;
            }
        };

        System.out.println("In test: " +
                Thread.currentThread().getName());
        System.out.println("Main priority: " +
                Thread.currentThread().getPriority());

        Future<Integer> future = executorService.submit(callable);

        //This will block
        Integer result = future.get(); //block
        System.out.println("result = " + result);
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
   }
}
