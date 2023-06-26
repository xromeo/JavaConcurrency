package com.rivera.functionalprogramming;

import java.util.StringJoiner;
/**
Composition of CompletableFutures with thenCompose
We now embark on an essential chain function, thenCompose. thenCompose is analogous to flatMap in functional programming. It is used to carry value from one context to another. For example, if I have one process that says that given a latitude and longitude, I want to find the closest city, but now that I have the closest city, I want the weather of that city. If we were to perform to chain this using thenApply, we would end up with CompletableFuture<CompletableFuture<Integer>>, which is undesireable in this case since we want CompletableFuture<Integer>. In other words, when this CompletableFuture finally resolves, we want an Integer—the answer—not another CompletableFuture.

In the following example, we find a city and state given some coordinates, and a custom class Coordinates.Then, given the city and state, we want to find the temperature given the string of the city and state. For a compositional chain like this, we use thenCompose:
*/
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main6 {

        public static class Coordinates {
                public double latitude;
                public double longitude;

                public Coordinates(double latitude, double longitude) {
                        this.latitude = latitude;
                        this.longitude = longitude;
                }

                @Override
                public String toString() {
                        return new StringJoiner(", ",
                                        Coordinates.class.getSimpleName() + "[", "]")
                                        .add("latitude=" + latitude)
                                        .add("longitude=" + longitude)
                                        .toString();
                }
        }

        public static CompletableFuture<String> findTheClosestCityState(final Coordinates coordinates) {
                return CompletableFuture.supplyAsync(() -> {
                        // Call Webservice to find the closest city and state
                        System.out.println("Finding the closest city and state with coordinates for " + coordinates);
                        return "Bloomington, MN";
                });
        }

        public static CompletableFuture<Integer> getTemperatureInFahrenheit(final String cityState) {
                return CompletableFuture.supplyAsync(() -> {
                        // Call Webservice to find the temperature for city and state
                        System.out.println("Finding the temperature for " + cityState);
                        return 75;
                });
        }

        public static void main(String[] args)
                        throws InterruptedException {
                ExecutorService executorService = Executors.newFixedThreadPool(4);
                CompletableFuture<String> theClosestCityState = findTheClosestCityState(
                                new Coordinates(44.8408, 93.2983));
                CompletableFuture<Integer> integerCompletableFuture = theClosestCityState
                                .thenCompose(Main6::getTemperatureInFahrenheit);
                integerCompletableFuture.thenAccept(System.out::println);
                Thread.sleep(100);
                executorService.shutdown();
                executorService.awaitTermination(4, TimeUnit.SECONDS);
        }
}