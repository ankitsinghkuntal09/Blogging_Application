package com.blog.services.impl;

import com.blog.services.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Async
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return new AsyncResult<String>("hello world !!!!");
        } catch (InterruptedException e) {
        }

        return null;
    }

    @Async("threadPoolTaskExecutor")
    public void asyncMethodWithConfiguredExecutor() {
        System.out.println("Execute method with configured executor - "
                + Thread.currentThread().getName());
    }

    @Async("threadPoolTaskExecutor1")
    public void asyncMethodWithConfiguredExecutor1() {
        System.out.println("Execute method with configured executor - "
                + Thread.currentThread().getName());
    }


    @Async
    public CompletableFuture<String> doAsyncOperation() {
        // Simulate some time-consuming task
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Async operation completed");
    }
}
