package com.multithreading;

/**
 * Created by Omar.Rivera on 28/2/23.
 *
 * @author Omar.Rivera
 */

public class State {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("[1] State = " + Thread.currentThread().getState());
        });

        System.out.println("[2] State = " + thread1.getState());

        thread1.start();

        System.out.println("[3] State = " + thread1.getState());

        thread1.join();

        System.out.println("[4] State = " + thread1.getState());

    }
}
