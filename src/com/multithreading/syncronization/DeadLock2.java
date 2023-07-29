package com.multithreading.syncronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock2 {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            lock1.lock();
            System.out.println("Thread 1 acquired lock1");

            lock2.lock();
            System.out.println("Thread 1 acquired lock2");
            lock2.unlock();

            lock1.unlock();
        });

        Thread thread2 = new Thread(() -> {
            lock2.lock();
            System.out.println("Thread 2 acquired lock2");

            lock1.lock();
            System.out.println("Thread 2 acquired lock1");            
            lock1.unlock();

            lock2.unlock();
        });

        thread1.start();
        thread2.start();

        System.out.println("Execution complete.");
    }
}
