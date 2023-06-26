package com.rivera.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Truly Running a Future Asynchronously
 * Up to this point, we haven't created anything truly asynchronous. The reason
 * we want to create a Future is to perform an operation without us tending to
 * it with get(), which will block.
 * 
 * In the following example, in the while loop we are doing work and continually
 * checking to see if the Future is done. It's just like letting the contractor
 * do their work and checking in every so often to see if they are done.
 * 
 * Once we've established that our contractor is done by calling isDone()
 * repeatedly, then we can call get() when we will not incur any penalty of
 * waiting. In the following example, notice !future.isDone(). We are asking the
 * Future, "Are we done?" If not, we are doing a little bit more work, printing
 * I am doing something else. We continue to this process until the Future is
 * indeed done.
 * 
 * The problem with this is that it is awkward. Keep in mind this is the API
 * from the mid-2000s—soon the Guava library would do something creative and
 * provide Java developers with callbacks, which we will see next:
 * 
 * We will then compile:
 * 
 * javac -d target src/com/rivera/Main3.java
 * 
 * Then we will run the following:
 * 
 * java -cp target com.rivera.Main3
 * 
 */

public class Main3 {
        public static void main(String[] args)
                        throws ExecutionException, InterruptedException {
                ExecutorService executorService = Executors.newCachedThreadPool();

                Callable<Integer> callable = () -> {
                        Thread.sleep(3000);
                        return 5 + 3;
                };

                Future<Integer> future = executorService.submit(callable);

                // NOTICE: This will not block, but it is awkward (extraño)
                while (!future.isDone()) {
                        System.out.println("I am doing something else on thread: " +
                                        Thread.currentThread().getName());
                }

                Integer result = future.get();
                System.out.println("result = " + result);
                executorService.shutdown();
                executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
}
