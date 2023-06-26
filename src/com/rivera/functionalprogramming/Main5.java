package com.rivera.functionalprogramming;

/**
 * Handling Exceptions with Handle
If you went through the lab on how to create CompletableFuture, you may have already seen handle. If not, we reintroduce it here. handle accepts a BiFunction, or a function with two parameters. The first will be assigned a non-null value if there were no issues, and the second will be assigned a non-null value if there was an issue. It is up to the developer to decide what to do in case either of those situations happen.

In the following example, we are using handle to handle a possible issue where parsing an Integer could end in failure. So we use handle to query. If the first argument is not null then we are successful, and if the second argument is not null, then we are not successful:*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main5 {
        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);
                CompletableFuture<String> stringFuture1 = CompletableFuture
                                .supplyAsync(() -> {
                                        try {
                                                System.out.println("stringFuture1 is sleeping in thread: "
                                                                + Thread.currentThread().getName());
                                                Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        return "Bloomington, MN";
                                }, executorService);

                stringFuture1.thenApply(Integer::parseInt).handle(
                                (item, throwable) -> {
                                        if (throwable == null)
                                                return item;
                                        else
                                                return -1;
                                }).thenAccept(System.out::println);

                System.out.println("This message should appear first.");
                Thread.sleep(1100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}
