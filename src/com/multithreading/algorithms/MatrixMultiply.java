package com.multithreading.algorithms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiply {

    private static int N = 5000;

    private static int[][] A = new int[N][N];
    private static int[][] B = new int[N][N];
    private static int[][] C = new int[N][N];

    private static void print(int[][] matrix) {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void multiply() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }

            }
        }
    }

    static void multiplyParallel() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(16);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                service.submit(new MultiplyTask(i, j));
            }
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

    }

    static class MultiplyTask implements Runnable {

        private final int i;
        private final int j;

        public MultiplyTask(int f, int c) {
            this.i = f;
            this.j = c;
        }

        @Override
        public void run() {
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }

    }

    private static void init() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                A[i][j] = 1;
                B[i][j] = 1;

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        init();
        long start = System.nanoTime();

        multiplyParallel();
        // multiply();

        long end = System.nanoTime();

        System.out.println("Executed time = " + (double) (end - start) / 1_000_000_000);

        // print(C);
    }
}
