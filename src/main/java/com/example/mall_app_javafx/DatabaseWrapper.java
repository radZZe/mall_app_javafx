package com.example.mall_app_javafx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseWrapper {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void executeInBackground(Runnable task) {
        executor.execute(task);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
