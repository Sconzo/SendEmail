package com.ti9.send.email.core.domain.service.tasks;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutorService {

    public static <T> void executeTasksInParallelAndWaitForThemAllToFinish(List<T> items, TaskProcessor<T> task) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<Void>> tasks = items.stream()
                    .map(item -> (Callable<Void>) () -> {
                        task.process(item);
                        return null;
                    }).toList();

            executor.invokeAll(tasks).forEach(future -> {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
