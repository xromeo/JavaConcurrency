package com.rivera.incompletablefuture;


 import java.util.StringJoiner;
import java.util.concurrent.*;
 /*
  * Using an Incomplete Future to Manually Trigger a Chain of Events
Needless to say, all incomplete CompletableFutures have the same API available as if you were to create a standard, eager CompletableFuture. As such, you can also combine and compose these Futures together if needed.

In the following example, let's use our thenCompose example. Instead of the Coordinates being provided immediately, we are going to wait a certain amount of time before answering. In order to do so, we will use CompletableFuture<Coordinates> coordinates = new CompletableFuture<>();. After a while, we will decide when to run the CompletableFuture and we do so at the end with the method complete:  */

public class Main2{
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

public static CompletableFuture<String>
findTheClosestCityState(final Coordinates coordinates) {
    return CompletableFuture.supplyAsync(() -> {
        //Call Webservice to find the closest city and state
        System.out.println("Finding the closest city and state with coordinates for " + coordinates);
        return "Bloomington, MN";
    });
}

public static CompletableFuture<Integer>
getTemperatureInFahrenheit(final String cityState) {
    return CompletableFuture.supplyAsync(() -> {
        //Call Webservice to find the temperature for city and state
        System.out.println("Finding the temperature for " + cityState);
        return 75;
    });
}


public static void main(String[] args)
    throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    //Promise is here
    CompletableFuture<Coordinates> coordinates = new CompletableFuture<>();

    CompletableFuture<String> theClosestCityState = coordinates.thenCompose(Main2::findTheClosestCityState);

    CompletableFuture<Integer> integerCompletableFuture =
        theClosestCityState.thenCompose(Main2::getTemperatureInFahrenheit);
    integerCompletableFuture.thenAccept(System.out::println);
    Thread.sleep(4000);
    coordinates.complete(new Coordinates(44.8408, 93.2983));

    Thread.sleep(100);
    executorService.shutdown();
    executorService.awaitTermination(4, TimeUnit.SECONDS);
}
}