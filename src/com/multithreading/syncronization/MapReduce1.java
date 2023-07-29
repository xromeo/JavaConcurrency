package com.multithreading.syncronization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/*   HDFS                             key|value
 * 
 * Data/Node1 ---> Map Operation ---> | Data1  |---> Reduce Operation ---> Output
 * Data/Node2 ---> Map Operation ---> | Data2  |---> Reduce Operation ---> Output
 * Data/Node3 ---> Map Operation ---> | Data3  |---> Reduce Operation ---> Output
 *  
 * 
 *  */
/*
 * Intermidiate result:
 * [
 *  friend : 1,
 *  a : 1,
 *  need:1,
 *  ...
 * ]
 * 
 * Reducers input:  
 * *[
 *      friend : 1,
 *      friend : 1
 *  ],
 *  [
 *      a : 1,
 *      a : 1
 *  ],
 *  [
 *      need:1
 *  ],
 *  ...
 * ] 
 * 
 */
public class MapReduce1 {

    public static final String data = "a friend in need is a friend indeed";

    private static final CountDownLatch countDownLatch = new CountDownLatch(2);

    public static final List<Map.Entry<String, Integer>> intermidiateResult = Collections
            .synchronizedList(new ArrayList<>());
    public static final List<List<Map.Entry<String, Integer>>> reducersInput = Collections
            .synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        List<String> input = Arrays.asList(data.split(" "));

        Thread thread1 = new Thread(new Mapper(input.subList(0, input.size() / 2)));
        thread1.start();

        Thread thread2 = new Thread(new Mapper(input.subList(input.size() / 2, input.size())));
        thread2.start();

        Thread partitioner = new Thread(new Partitioner());
        partitioner.start();
        partitioner.join();

        for (List<Map.Entry<String, Integer>> reducerInput : reducersInput) {
            new Thread(new Reducer(reducerInput)).start();
        }
    }

    public static class Mapper implements Runnable {

        private final List<String> input;

        public Mapper(List<String> input) {
            this.input = input;
        }

        @Override
        public void run() {
            for (String word : input) {
                intermidiateResult.add(Map.entry(word, 1));
            }
            countDownLatch.countDown();
        }
    }

    public static class Partitioner implements Runnable {

        @Override
        public void run() {

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> uniqueWords = intermidiateResult.stream()
                    .map(t -> t.getKey())
                    .distinct()
                    .collect(Collectors.toList());

            for (String word : uniqueWords) {
                List<Entry<String, Integer>> reducerInput = intermidiateResult.stream()
                        .filter(entry -> entry.getKey().equals(word))
                        .collect(Collectors.toList());
                reducersInput.add(reducerInput);
            }

        }

    }

    public static class Reducer implements Runnable {

        List<Map.Entry<String, Integer>> reducerInput;

        public Reducer(List<Entry<String, Integer>> reducerInput) {
            this.reducerInput = reducerInput;
        }

        @Override
        public void run() {
            int S = 0;
            for (Map.Entry<String, Integer> entry : reducerInput) {
                S += entry.getValue();
            }

            System.out.println("The word: " + reducerInput.get(0).getKey() + " -> occurrences: " + S);

        }

    }

}
