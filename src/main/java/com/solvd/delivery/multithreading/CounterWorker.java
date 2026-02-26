package com.solvd.delivery.multithreading;

public class CounterWorker implements Runnable {

    @Override
    public void run() {
        SingletonCounter counter = SingletonCounter.getInstance();
        int value = counter.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " - " + value);
    }
}
