package com.multithreading.syncronization;

import java.util.concurrent.Phaser;

import com.multithreading.syncronization.CountDownLatch1.WorkerThread;

public class Phaser1 {

    private static int S = 0;
    private static int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };

    private static Phaser phaser = new Phaser(1);//main thread

    static class WorkerThread implements Runnable {

        private final int threadIndex;

        public WorkerThread(int threadIndex) {
            this.threadIndex = threadIndex;
            phaser.register();
        }

        @Override
        public void run() {
            array[threadIndex] = array[threadIndex] * 2;
            phaser.arriveAndAwaitAdvance();
            System.out.println("0 # " +  phaser.getRegisteredParties());
            if (threadIndex == 0) {
                for (int j : array) {
                    S = S + j;
                }
                System.out.println("1 # " +  phaser.getRegisteredParties());
                phaser.arriveAndAwaitAdvance();
                System.out.println("2 # " +  phaser.getRegisteredParties());
            } else {
                phaser.arriveAndDeregister();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < array.length; i++) {
            Thread t = new Thread(new WorkerThread(i));
            t.start();
        }
        System.out.println("3 # " +  phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println("4 # " +  phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println("5 # " +  phaser.getRegisteredParties());

        System.out.println("The sum is: " + S);
        System.out.println("Phase count: " + phaser.getPhase());
    }

}
