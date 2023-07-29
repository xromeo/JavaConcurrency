package com.multithreading.algorithms;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import com.multithreading.algorithms.QuickSortParallel.QuickSortTask;

public class QuickSort {

    private static Random random = new Random();
    private static int arraySize = 100_000_000;
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

    static void quickSort(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);

            quickSort(left, pivotIndex - 1);
            quickSort(pivotIndex + 1, right);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(arraySize);
        }

        long start = System.nanoTime();

        // System.out.println(Arrays.toString(array));

        quickSort(0, arraySize - 1);

        long end = System.nanoTime();

        System.out.println("Executed time = " + (double) (end - start) / 1_000_000_000);

        // System.out.println(Arrays.toString(array));
    }
}
