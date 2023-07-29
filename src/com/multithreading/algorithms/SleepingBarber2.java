package com.multithreading.algorithms;

import java.util.concurrent.Semaphore;

class BarberShop {
    private Semaphore barberSemaphore;
    private Semaphore customerSemaphore;
    private Semaphore accessSeats;
    private int numberOfWaitingSeats;

    public BarberShop(int numberOfWaitingSeats) {
        this.numberOfWaitingSeats = numberOfWaitingSeats;
        barberSemaphore = new Semaphore(0);
        customerSemaphore = new Semaphore(0);
        accessSeats = new Semaphore(1);
    }

    public void barber() {
        while (true) {
            try {
                customerSemaphore.acquire(); // Wait for a customer to arrive
                accessSeats.acquire(); // Try to acquire access to waiting seats
                numberOfWaitingSeats++; // Increment available waiting seats
                barberSemaphore.release(); // Wake up the barber
                accessSeats.release(); // Release access to waiting seats
                cutHair(); // Barber cuts the hair
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void customer(int customerId) {
        try {
            accessSeats.acquire(); // Try to acquire access to waiting seats
            if (numberOfWaitingSeats > 0) {
                numberOfWaitingSeats--; // Decrement available waiting seats
                customerSemaphore.release(); // Signal the barber that a customer has arrived
                accessSeats.release(); // Release access to waiting seats
                barberSemaphore.acquire(); // Wait for the barber to be available
                getHaircut(customerId); // Customer gets a haircut
            } else {
                accessSeats.release(); // Release access to waiting seats
                leaveBarberShop(customerId); // Customer leaves as there are no empty seats
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cutHair() {
        System.out.println("Barber is cutting hair.");
        try {
            Thread.sleep(2000); // Simulate time for cutting hair
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getHaircut(int customerId) {
        System.out.println("Customer " + customerId + " is getting a haircut.");
        try {
            Thread.sleep(3000); // Simulate time for getting a haircut
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void leaveBarberShop(int customerId) {
        System.out.println("Customer " + customerId + " leaves the barber shop.");
    }
}

class Barber extends Thread {
    private BarberShop barberShop;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        barberShop.barber();
    }
}

class Customer extends Thread {
    private BarberShop barberShop;
    private int customerId;

    public Customer(BarberShop barberShop, int customerId) {
        this.barberShop = barberShop;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        barberShop.customer(customerId);
    }
}

public class SleepingBarber2 {
    public static void main(String[] args) {
        int numberOfWaitingSeats = 5;
        BarberShop barberShop = new BarberShop(numberOfWaitingSeats);

        Barber barber = new Barber(barberShop);
        barber.start();

        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(barberShop, i);
            customer.start();
            try {
                Thread.sleep(1000); // Simulate time between customers arriving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
