package com.multithreading;


public class ThreadGroup1 {
 
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup1");    

        Thread thread1 = new Thread(threadGroup, () -> System.out.println("Thread1"));
        Thread thread2 = new Thread(threadGroup, () -> System.out.println("Thread2"));
        Thread thread3 = new Thread(threadGroup, () -> System.out.println("Thread3"));

        thread1.start();
        thread2.start();
        thread3.start();


        System.out.println("Sleeping for 3 seconds ...  "); 

        Thread.sleep(3000);

        threadGroup.interrupt();
        //Thread thread1 = new Thread(new MyThread(10), "Thread1");
 
//        thread1.start();

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
