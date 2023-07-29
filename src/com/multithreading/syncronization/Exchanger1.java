package com.multithreading.syncronization;

import java.util.concurrent.Exchanger;

/*
 * the Exchanger class is a synchronization barrier that allows two threads to exchange objects between them at a certain synchronization point. It provides a simple and convenient way for two threads to swap objects in a concurrent environment.

Here's a brief explanation of how the Exchanger class works:

Creating an Exchanger instance: To use the Exchanger class, you first need to create an instance of it. You can create it by calling the Exchanger constructor, which takes a generic type parameter representing the type of objects to be exchanged. For example:

java code

Exchanger<String> exchanger = new Exchanger<>();

Exchanging objects: Once you have an Exchanger instance, two threads can participate in an exchange by calling the exchange() method. When a thread invokes exchange(), it will block until another thread also calls exchange(). At that point, the objects held by the two threads are swapped.


 */
public class Exchanger1 {
    
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        Thread t = new Thread(() -> {
            try {
                String receivedValue = exchanger.exchange("data2");
                System.out.println("Received: " + receivedValue + " in thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t.start();
        
        String receivedValue = exchanger.exchange("data1");
        System.out.println("Received: " + receivedValue + " in thread " + Thread.currentThread().getName());

    }
}
