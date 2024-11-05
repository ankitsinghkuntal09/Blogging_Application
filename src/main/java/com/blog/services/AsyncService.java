package com.blog.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncService {

    public Future<String> asyncMethodWithReturnType() ;
    public void asyncMethodWithConfiguredExecutor1() ;
    public void asyncMethodWithConfiguredExecutor() ;
    public CompletableFuture<String> doAsyncOperation();

}
