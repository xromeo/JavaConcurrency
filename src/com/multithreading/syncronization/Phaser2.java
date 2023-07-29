package com.multithreading.syncronization;

import java.util.concurrent.Phaser;

public class Phaser2 {

    public static void main(String[] args) {
        int numberOfThreads = 3;
        Phaser phaser = new Phaser(numberOfThreads) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("All threads have advanced to the next phase: " + phase);
                return phase >= 2 || registeredParties == 0;
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new WorkerThread(phaser));
            thread.start();
        }
    }

    static class WorkerThread implements Runnable {
        private final Phaser phaser;

        public WorkerThread(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread is at phase: " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

}
