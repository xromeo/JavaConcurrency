package com.rivera.future;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
Parameterizing a Future
Circling back to the standard library in Java, how does one parameterize a Future? For example, say I wanted to get the temperature from particular city. The signature for Future doesn't have the possibility to provide a parameter. To do this, we will perform the following:

Create a method that accepts the parameter
Create a Future closure to capture the parameter of the method
Return the Future
In this example, we will use a parameter url to see if the word Java is in the webpage for that we provide in a method. Notice that downloadingContentFromURL returns a Future that will inevitably go through every line and remove the HTML tags.

Once complete, we will use functional-style programming to find all lines with the word Java:
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

public class Main5 {
        private static ExecutorService executorService;

    private static Future<Stream<String>> downloadingContentFromURL(final String url) {
        return executorService.submit(() -> {
            URL netUrl = new URL(url);
            URLConnection urlConnection = netUrl.openConnection();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    urlConnection.getInputStream()));
            return reader
                .lines()
                .map(ln -> ln.replaceAll("<(/?[^>]+)>", "\\ ").replaceAll("\\s+", " ").trim());
        });
    }

    public static void main(String[] args)
        throws InterruptedException, ExecutionException {

        executorService = Executors.newCachedThreadPool();

        Future<Stream<String>> future =
            downloadingContentFromURL("https://learning.oreilly.com/library/view/java-in-a/9781492037248/");

        while (!future.isDone()) {
            Thread.sleep(1000);
            System.out.println("Doing Something Else");
        }
        Stream<String> allStrings = future.get();
        allStrings
            .filter(x -> x.contains("Java"))
            .forEach(System.out::println);
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
