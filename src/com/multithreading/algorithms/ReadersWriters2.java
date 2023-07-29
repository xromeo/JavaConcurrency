package com.multithreading.algorithms;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadersWriters2 {

    private static final int NUM_READERS = 5;
    private static final int NUM_WRITERS = 2;

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        // Create and start reader threads
        for (int i = 0; i < NUM_READERS; i++) {
            Thread reader = new Thread(new Reader(sharedResource, readWriteLock, i + 1));
            reader.start();
        }

        // Create and start writer threads
        for (int i = 0; i < NUM_WRITERS; i++) {
            Thread writer = new Thread(new Writer(sharedResource, readWriteLock, i + 1));
            writer.start();
        }
    }
}

class SharedResource {
    private int data = 0;

    public int readData() {
        return data;
    }

    public void writeData(int newData) {
        data = newData;
    }
}

class Reader implements Runnable {
    private final SharedResource sharedResource;
    private final ReadWriteLock readWriteLock;
    private final int readerId;

    public Reader(SharedResource sharedResource, ReadWriteLock readWriteLock, int readerId) {
        this.sharedResource = sharedResource;
        this.readWriteLock = readWriteLock;
        this.readerId = readerId;
    }

    public void run() {
        try {
            while (true) {
                readWriteLock.readLock().lock();
                try {
                    int data = sharedResource.readData();
                    System.out.println("Reader " + readerId + " read: " + data);
                } finally {
                    readWriteLock.readLock().unlock();
                }
                Thread.sleep(1000); // Simulate some work being done after reading
            }
        } catch (InterruptedException e) {
            System.err.println("Reader " + readerId + " was interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}

class Writer implements Runnable {
    private final SharedResource sharedResource;
    private final ReadWriteLock readWriteLock;
    private final int writerId;
    private static int count = 0;

    public Writer(SharedResource sharedResource, ReadWriteLock readWriteLock, int writerId) {
        this.sharedResource = sharedResource;
        this.readWriteLock = readWriteLock;
        this.writerId = writerId;
    }

    public void run() {
        try {
            while (true) {
                readWriteLock.writeLock().lock();
                try {
                    int newData = ++count;
                    sharedResource.writeData(newData);
                    System.out.println("Writer " + writerId + " wrote: " + newData);
                } finally {
                    readWriteLock.writeLock().unlock();
                }
                Thread.sleep(2000); // Simulate some work being done after writing
            }
        } catch (InterruptedException e) {
            System.err.println("Writer " + writerId + " was interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
