package com.multithreading.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 
The Dining Philosophers problem is a classic synchronization problem in computer science that illustrates challenges in resource allocation and concurrency control. It was first proposed by Edsger Dijkstra in 1965.

The problem involves a scenario where a group of philosophers are sitting around a table with a bowl of rice and a single chopstick between each pair of philosophers. The philosophers alternate between thinking and eating. To eat, a philosopher must pick up both chopsticks adjacent to them.

The challenge arises when multiple philosophers try to pick up chopsticks simultaneously. If all philosophers attempt to pick up their left chopstick at the same time, for example, they will be stuck waiting indefinitely for the right chopstick, resulting in a deadlock.

To solve the Dining Philosophers problem, various synchronization techniques can be employed, such as using mutex locks, semaphores, or monitors. These techniques ensure that only a certain number of philosophers can pick up the chopsticks simultaneously, preventing deadlocks.

Several solutions have been proposed over the years, including Dijkstra's original solution using semaphores and more advanced solutions like resource hierarchy and arbitration algorithms. These solutions aim to provide fairness and prevent deadlocks while allowing philosophers to eat as efficiently as possible.

The Dining Philosophers problem is often used as a case study in concurrent programming and operating system courses to illustrate the challenges of synchronization and the need for proper resource management in parallel systems.
 */

// Dead Lock

public class DiningPhilosophersPart1 {

    private static List<Lock> forks = new ArrayList();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            forks.add(new ReentrantLock());
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(i)).start();            
        }

    }

    static class Philosopher implements Runnable {

        private final int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                think();
                pickForks();
                eat();
                putForks();
            }
        }

        private void pickForks() {
            forks.get(id).lock();
            System.out.println("Philosopher " + id + " picked the right fork");
            forks.get((id + 1) % 5).lock();
            System.out.println("Philosopher " + id + " picked the left fork");
        }

        private void eat() {
            System.out.println("Philosopher " + id + " eats");
        }

        private void putForks() {
            forks.get(id).unlock();
            forks.get((id + 1) % 5).unlock();
        }

        private void think() {
            System.out.println("Philosopher " + id + " thinks");
        }

    }
}
