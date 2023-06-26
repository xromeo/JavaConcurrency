package com.rivera.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Parameterizing a CompletableFuture
 * How does one parameterize a CompletableFuture? For example, say I wanted to
 * get the temperature from a particular city. The signature for
 * CompletableFuture doesn't have the ability to provide a parameter. If you
 * visited our previous lab, you saw how we did this with a regular Future. To
 * do this with a CompletableFuture, we do the same, and we will perform the
 * following:
 * 
 * Create a method that accepts the parameter
 * Create a CompletableFuture closure to capture the parameter of the method
 * Return the CompletableFuture
 * In this example, we want to call a web service that looks up any city or
 * state's temperature. We have a getTemperatureInFahrenheit method that accepts
 * the name of the city and state, and we close around that variable and return
 * the CompleteableFuture. We then process the CompletableFuture in the main
 * method:
 * 
 * We will then compile:
 * 
 * javac -d target src/com/rivera/Main.java
 * 
 * Then we will run the following:
 * 
 * java -cp target com.rivera.Main
 * 
 */

public class Main2 {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static CompletableFuture<Integer> getTemperatureInFahrenheit(final String cityState) {
        return CompletableFuture.supplyAsync(() -> {
            // We go into a webservice to find the weather...
            System.out.println("In getTemperatureInFahrenheit: " +
                    Thread.currentThread().getName());
            System.out.println("Finding the temperature for " + cityState);
            return 75;
        }, executorService);
    }

    public static void main(String[] args)
            throws InterruptedException {

        CompletableFuture<Integer> temperatureInSacramentoFuture = getTemperatureInFahrenheit("Sacramento, CA");

        temperatureInSacramentoFuture.thenAccept(System.out::println);

        executorService.shutdown();
        executorService.awaitTermination(4, TimeUnit.SECONDS);
    }
}
