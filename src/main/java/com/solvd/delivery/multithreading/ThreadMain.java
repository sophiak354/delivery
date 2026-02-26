package com.solvd.delivery.multithreading;

import java.util.List;
import java.util.stream.IntStream;

public class ThreadMain {
    public static void runSingletonThreadsDemo(int threadsCount) {

        SingletonCounter.getInstance().reset();

        List<Thread> threads = IntStream.rangeClosed(1, threadsCount)
                .mapToObj(i -> new Thread(
                        new CounterWorker(),
                        "Worker-" + i
                ))
                .toList();

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Final counter: " + SingletonCounter.getInstance().get());
    }
}
