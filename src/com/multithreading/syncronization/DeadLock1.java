package com.multithreading.syncronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
 * In this example, Thread 1 and Thread 2 each attempt to acquire two locks (lock1 and lock2) in a different order. Thread 1 acquires lock1 and then waits for lock2, while Thread 2 acquires lock2 and then waits for lock1. This creates a circular dependency between the two threads, resulting in a deadlock.

When you run this code, you will likely observe that the program becomes stuck and does not progress. Both threads are waiting for the locks held by the other thread, leading to a deadlock situation.

To avoid deadlock in such scenarios, it's important to ensure consistent and ordered acquisition of locks across threads. You can achieve this by defining a specific ordering for acquiring locks and ensuring that all threads follow the same order. This helps prevent circular dependencies and potential deadlocks.

In the example above, you could modify the order of acquiring locks in one of the threads, ensuring that both threads acquire locks in the same order, thus avoiding the deadlock.
 */
public class DeadLock1 {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            lock1.lock();
            try {
                System.out.println("Thread 1 acquired lock1");
                Thread.sleep(1000);// Introducing a delay to allow Thread 2 to acquire lock2
                lock2.lock();
                try {
                    System.out.println("Thread 1 acquired lock2");
                } finally {
                    lock2.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
            }
        });


        Thread thread2 = new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("Thread 2 acquired lock2");
                lock1.lock();
                try {
                    System.out.println("Thread 2 acquired lock1");
                } finally {
                    lock1.unlock();
                }
            } finally {
                lock2.unlock();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Execution complete.");
    }
}
