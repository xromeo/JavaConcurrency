package com.multithreading.algorithms;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class NoStarveMutex {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition queue = lock.newCondition();
    private boolean isLocked = false;

    public void lock() {
        lock.lock();
        try {
            // Wait while the mutex is already locked
            while (isLocked) {
                queue.await();
            }
            isLocked = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void unlock() {
        lock.lock();
        try {
            isLocked = false;
            queue.signalAll(); // Signal waiting threads that the mutex is available
        } finally {
            lock.unlock();
        }
    }
}

class WorkerThread implements Runnable {
    private final NoStarveMutex mutex;
    private final String name;

    public WorkerThread(NoStarveMutex mutex, String name) {
        this.mutex = mutex;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            mutex.lock();
            try {
                System.out.println(name + " is inside the critical section.");
                Thread.sleep(1000); // Simulate work inside the critical section
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(name + " is leaving the critical section.");
                mutex.unlock();
                try {
                    Thread.sleep(1000); // Simulate work outside the critical section
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class NoStarveMutexExample {
    public static void main(String[] args) {
        NoStarveMutex mutex = new NoStarveMutex();

        Thread thread1 = new Thread(new WorkerThread(mutex, "Thread 1"));
        Thread thread2 = new Thread(new WorkerThread(mutex, "Thread 2"));

        thread1.start();
        thread2.start();
    }
}

