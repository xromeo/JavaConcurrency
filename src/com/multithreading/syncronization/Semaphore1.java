package com.multithreading.syncronization;

import java.util.concurrent.Semaphore;

public class Semaphore1 {

    private static Semaphore semaphore = new Semaphore(2);

    static class Executor {
        public void submit(Job job) throws InterruptedException {

            System.out.println("Launching job " + job.getWork());
            semaphore.acquire();

            Thread t = new Thread(() -> {

                try {
                    System.out.println("Executing Job " + job.getWork());

                    Thread.sleep(job.getWork());

                    semaphore.release(job.getWork());
                    System.out.println("Job finished with id " + job.getWork());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            t.start();
        }
    }

    static class Job {

        private final int work;

        public Job(int work) {
            this.work = work;
        }

        public int getWork() {
            return this.work;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Executor executor = new Executor();

        executor.submit(new Job(4000));
        executor.submit(new Job(5000));
        executor.submit(new Job(3000));
    }
}
