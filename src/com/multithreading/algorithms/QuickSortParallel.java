package com.multithreading.algorithms;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

public class QuickSortParallel {

    private static Random random = new Random();
    private static int arraySize = 100000000;
    private static int[] array = new int[arraySize];

    static int partition(int left, int right) {
        int pivot = array[right];
        int swapIndex = left - 1;

        for (int i = left; i < right; i++) {
            if (array[i] < pivot) {
                swapIndex++;
                swap(swapIndex, i);
            }
        }

        swapIndex++;
        swap(swapIndex, right);
        return swapIndex;
    }

    static void swap(int left, int right) {
        int aux = array[left];
        array[left] = array[right];
        array[right] = aux;
    }

    static class QuickSortTask extends RecursiveAction {

        private final int left;
        private final int right;

        public QuickSortTask(int left, int right) {
            this.left = left;
            this.right = right;

        }

        @Override
        protected void compute() {
            if (left < right) {
                int pivotIndex = partition(left, right);

                invokeAll(new QuickSortTask(left, pivotIndex - 1),
                        new QuickSortTask(pivotIndex + 1, right));
            }
        }

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(arraySize);
        }

        //System.out.println(Arrays.toString(array));
        long start = System.nanoTime();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<?> feature = forkJoinPool.submit(new QuickSortTask(0, arraySize - 1));

        feature.get();

        long end = System.nanoTime();

        System.out.println("Executed time = " + (double)(end - start)/1_000_000_000);

        //System.out.println(Arrays.toString(array));
    }
}
