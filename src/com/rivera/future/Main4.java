package com.rivera.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Using Guava to Create a Callback
 * Guava is libary that continues to make Java easier to use. It was developed
 * by Google in 2007. Some advancements include the ListenerFuture and
 * ListenerExecutorService.
 * 
 * ListenableExecutorService is a wrapper around a standard Java
 * ExecutorService. This ListenableExecutorService will allow us to submit our
 * Callable in place of the thread pool and will return a ListenableFuture. A
 * ListenableFuture is a wrapper around the standard Future, which provides
 * callback functionality.
 * 
 * Notice the call Futures.addCallback, this is our future callback. The
 * callback has two methods: onSuccess and onFailure. This solution is far
 * better than creating a while loop and continually checking into our Future to
 * see if it is done.
 * 
 * Going back to our contractor analogy, instead of continually checking in with
 * our contractor, in this case we will simply be alerted when they are done:
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

public class Main4 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newCachedThreadPool();

//                ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
//
//                ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(
//                                () -> {
//                                        Thread.sleep(2000);
//                                        return 33 + 40;
//                                });
//
//                Futures.addCallback(listenableFuture,
//                                new FutureCallback<>() {
//                                        @Override
//                                        public void onSuccess(Integer result) {
//                                                System.out.println(
//                                                                "Got the result and the answer is? " + result);
//                                        }
//
//                                        @Override
//                                        public void onFailure(Throwable t) {
//                                                System.out.println("Things happened man. Bad things" + t.getMessage());
//                                        }
//                                }, executorService);

                System.out.println("This should come before the result, I think!");
                Thread.sleep(5000);
                executorService.shutdown();
                executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
}
