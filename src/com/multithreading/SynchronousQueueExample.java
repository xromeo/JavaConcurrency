package com.multithreading;

import java.util.concurrent.SynchronousQueue;
/*
 * 
 * In this example, we create a SynchronousQueue named synchronousQueue. We then start two threads, a producer thread and a consumer thread.

The producer thread uses the put method to put an element (in this case, the integer value 42) into the queue. The put method blocks until the consumer thread takes the element.

The consumer thread uses the take method to take the element from the queue. The take method blocks until the producer thread puts an element into the queue.

When you run this code, you'll see that the producer thread puts the element into the SynchronousQueue, and the consumer thread takes it immediately, demonstrating the synchronous nature of the queue.

SynchronousQueue is commonly used when you want to implement a handoff scenario between producer and consumer threads, where the producer thread blocks until the consumer thread is ready to take the element, and vice versa.
 * 
 */
public class SynchronousQueueExample {
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        // Producer thread
        new Thread(() -> {
            try {
                int producedItem = 42;
                synchronousQueue.put(producedItem); // Block until consumer takes the item
                System.out.println("Producer put: " + producedItem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Consumer thread
        new Thread(() -> {
            try {
                int consumedItem = synchronousQueue.take(); // Block until producer puts the item
                System.out.println("Consumer took: " + consumedItem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
