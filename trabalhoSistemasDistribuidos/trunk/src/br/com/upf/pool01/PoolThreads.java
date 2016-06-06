package br.com.upf.pool01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolThreads {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker;
            worker = new ThreadWorking("" + i);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}