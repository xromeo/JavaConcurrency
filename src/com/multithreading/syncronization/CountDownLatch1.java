package com.multithreading.syncronization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch1
 */
public class CountDownLatch1 {

    private static int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
    private static int foundPosition = 0;
    private static int numberOfThreads = 3;
    private static int numberToSearch = 6;

    private static CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);

    static class WorkerThread implements Runnable {

        private static int left;
        private static int right;

        public WorkerThread(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {

            for (int i = left; i < right; i++) {
                if (array[i] == numberToSearch) {
                    foundPosition = i;
                }
            }
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int threadSlice = array.length / numberOfThreads ;

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new WorkerThread(i * threadSlice, (i + 1) * threadSlice));
            thread.start();
        }

        countDownLatch.await();

        System.out.println("The number was found on position " + foundPosition);
    }
}