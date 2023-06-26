package com.rivera.future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Creating a Future but Blocked
 * The following code is the same as the previous step. The only difference is
 * the Callable we used before has been reduced into a lambda. Any anonymous
 * instantiation of an interface like Callable can reduced into (args...) ->
 * function(args):
 * We will then compile:
 * 
 * javac -d target src/com/rivera/Main2.java
 * 
 * Then we will run the following:
 * 
 * java -cp target com.rivera.Main2
 * 
 */

public class Main2 {
        public static void main(String[] args)
                        throws ExecutionException, InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(5);

                // Notice: This is a lambda
                Callable<Integer> callable = () -> {
                        System.out.println("Inside the future: " +
                                        Thread.currentThread().getName());
                        System.out.println("Future priority: "
                                        + Thread.currentThread().getPriority());
                        Thread.sleep(5000);
                        return 5 + 3;
                };

                System.out.println("In test: " +
                                Thread.currentThread().getName());
                System.out.println("Main priority: " +
                                Thread.currentThread().getPriority());

                Future<Integer> future = executorService.submit(callable);

                // This will block
                Integer result = future.get(); // block
                System.out.println("result = " + result);
                executorService.shutdown();
                executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
}
