package com.multithreading.management;

/**
 * Created by Omar.Rivera on 28/2/23.
 *
 * @author Omar.Rivera
 */

public class Priority {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Thread = " + Thread.currentThread().getName() + " Priority: " + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        Thread thread1 = new Thread(
                () -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Thread = " + Thread.currentThread().getName() + " Priority: " + Thread.currentThread().getPriority());
                }
        );

        thread1.setName("Thread_1");
        thread1.setPriority(Thread.MAX_PRIORITY);


        Thread thread2 = new Thread(
                () -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Thread = " + Thread.currentThread().getName() + " Priority: " + Thread.currentThread().getPriority());
                }
        );

        thread2.setName("Thread_2");
        thread2.setPriority(Thread.MIN_PRIORITY);


        thread1.start();
        thread2.start();

        thread1.join(1000);

        System.out.println("thread1.getState() = " + thread1.getState());

        thread2.join();

    }
}
