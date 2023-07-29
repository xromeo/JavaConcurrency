package com.multithreading.syncronization;

import java.util.ArrayList;
import java.util.List;

//Multiples thread are using the same resource

public class Synchronized1 {
 
    private static int globalCounter = 0;
    public static final Object obj = new Object();


    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        ThreadGroup group = new ThreadGroup("Group1");

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(group, new MyThread("Thread-" + i));
            thread.start();
            threads.add(thread);
        }

        group.interrupt();

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Total = " + globalCounter);
    }

    
    static class MyThread implements Runnable{

        public MyThread(String name) {
            Thread.currentThread().setName(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
                //System.out.println("Interrupted = " + Thread.currentThread().getName());
            }
            synchronized(obj){
                globalCounter++;
            }
        }

    }

}
