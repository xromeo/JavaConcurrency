package com.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Lock2 {

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    private static List<Integer> list = new ArrayList<>();

    static class WriterThread implements Runnable {

        @Override
        public void run() {
            while(true){
                try {
                    writeData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void writeData() throws InterruptedException {
            Thread.sleep(10000);
            writeLock.lock();

            int value = new Random().nextInt(10);
            System.out.println("Producing data: " + value);

            list.add(value);

            writeLock.unlock();
        }

    }

    static class ReaderThread implements Runnable {

        @Override
        public void run() { 
            while(true){
                try {
                    readData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void readData() throws InterruptedException {
            Thread.sleep(4000);

            readLock.lock();
            System.out.println(list);
            readLock.unlock();
        }


    }

    public static void main(String[] args) {

        Thread writer = new Thread(new WriterThread());
        Thread reader1 = new Thread(new ReaderThread());
        Thread reader2 = new Thread(new ReaderThread());
        Thread reader3 = new Thread(new ReaderThread());
        Thread reader4 = new Thread(new ReaderThread());
        Thread reader5 = new Thread(new ReaderThread());

        writer.start();
        reader1.start();
        reader2.start();
        reader3.start();
        reader4.start();
        reader5.start();
    }

}
