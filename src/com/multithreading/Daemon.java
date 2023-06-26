package com.multithreading;


public class Daemon {
 
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyThread(10), "Thread1");
        Thread thread2 = new Thread(new MyThread(2), "Thread2");

        thread1.setDaemon(true);

        thread1.start();
        thread2.start();
        
        //thread1.join();
    }

    static class MyThread implements Runnable{

        private final int numberOfSeconds;

        public MyThread(int numberOfSeconds) {
            this.numberOfSeconds = numberOfSeconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfSeconds; i++) {
                try {
                    System.out.println("Sleeping for 1s, thread: " + Thread.currentThread().getName());
                    Thread.sleep(01000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
