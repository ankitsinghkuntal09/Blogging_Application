package com.blog.controllers;

import com.blog.services.AsyncService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;


@RestController
@Hidden
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @Async //it will enable async on this api.
    @GetMapping("/slow-operation-error")
    public String slowOperationError() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
        return "Slow operation completed";
    }
    // java.lang.IllegalArgumentException: Invalid return type for async method (only Future and void supported):

    @Async
    @GetMapping("/slow-operation")
    public void slowOperation() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
        //Output: it means by default SimpleAsyncTaskExecutor is used.
        //        Execute method asynchronously. SimpleAsyncTaskExecutor-1
        //        Execute method asynchronously. SimpleAsyncTaskExecutor-2
        //        Execute method asynchronously. SimpleAsyncTaskExecutor-3
        return ;
    }

    //http://localhost:8080/slow-operation-configure-executor
    @GetMapping("/slow-operation-configure-executor")
    public void slowOperation2() throws InterruptedException {
        asyncService.asyncMethodWithConfiguredExecutor();
        Thread.sleep(5000);
        System.out.println("Execute method asynchronously. with asyncMethodWithConfiguredExecutor "
                + Thread.currentThread().getName());
        //Output:
//        Execute method with configured executor - threadPoolTaskExecutor-1
//        Execute method asynchronously. with asyncMethodWithConfiguredExecutor http-nio-8080-exec-2
        return ;
    }

    //http://localhost:8080/slow-operation-configure-executor1
    @GetMapping("/slow-operation-configure-executor1")
    public void slowOperation3() throws InterruptedException {
        asyncService.asyncMethodWithConfiguredExecutor1();
        Thread.sleep(5000);
        System.out.println("Execute method asynchronously. with asyncMethodWithConfiguredExecutor "
                + Thread.currentThread().getName());
        //Ouput:
        //Execute method with configured executor - CustomExecutor1::1
        //Execute method asynchronously. with asyncMethodWithConfiguredExecutor http-nio-8080-exec-1
        return ;
    }



    @Async
    @GetMapping("/slow-operation-map")
    public Future<String> slowOperation1() throws InterruptedException {
        Thread.sleep(5000);

        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());

        if (Math.random() < 0.5) {
            System.out.println("Execute method asynchronously1111. "
                    + Thread.currentThread().getName());

            throw new RuntimeException("Something went wrong!");
        }
        return new AsyncResult<>("Slow operation completed");
    }

    //http://localhost:8080/slow-operation-returntype
    @GetMapping("/slow-operation-returntype")
    public void testAsyncAnnotationForMethodsWithReturnType()
            throws InterruptedException, ExecutionException {

        System.out.println("Invoking an asynchronous method. "
                + Thread.currentThread().getName());

        Future<String> future = asyncService.asyncMethodWithReturnType();

        while (true) {
            if (future.isDone()) {
                System.out.println("Result from asynchronous process - " + future.get());
                break;
            }
            System.out.println("Continue doing something else. ");
            Thread.sleep(1000);
            //Output: when @async is written above asyncMethodWithReturnType() in AsyncServiceImpl
//            Invoking an asynchronous method. http-nio-8080-exec-2
//            2024-03-20T02:42:20.318+05:30  INFO 52435 --- [nio-8080-exec-2] .s.a.AnnotationAsyncExecutionInterceptor : More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'. Mark one of them as primary or name it 'taskExecutor' (possibly as an alias) in order to use it for async processing: [threadPoolTaskExecutor1, threadPoolTaskExecutor2, threadPoolTaskExecutor]
//            Continue doing something else.
//            Execute method asynchronously - SimpleAsyncTaskExecutor-1
//            Continue doing something else.
//            Continue doing something else.
//            Continue doing something else.
//            Continue doing something else.
//            Result from asynchronous process - hello world !!!!

            //Output: when @async is commented above asyncMethodWithReturnType() in AsyncServiceImpl
//            Invoking an asynchronous method. http-nio-8080-exec-2
//            Execute method asynchronously - http-nio-8080-exec-2
//            Result from asynchronous process - hello world !!!!
        }
    }

    //http://localhost:8080/async-operation
    @GetMapping("/async-operation")
    public CompletableFuture<String> performAsyncOperation() {
        return asyncService.doAsyncOperation();
    }

}

// REFER THE BELOW SITE FOR IMPLEMENTATTION
//https://medium.com/@bubu.tripathy/a-beginners-guide-to-async-processing-in-a-spring-boot-application-a4c785a992f2#:~:text=To%20enable%20async%20support%20in,threads%20to%20handle%20async%20requests


// https://www.baeldung.com/spring-async

// https://www.linkedin.com/pulse/asynchronous-calls-spring-boot-using-async-annotation-omar-ismail/

//Make the Slow Operation Async
//modify the slowOperation method in our DemoController class to make it async.
//To do this, we will add the @Async annotation to the method.
//This annotation will tell Spring to run the method in a separate thread from the main thread.

//After adding the @Async annotation ,
//the slowOperation method will be executed in a separate thread from the main thread.
//This means that the main thread will not be blocked while the slow operation is running.


//  --> http://localhost:8080/slow-operation

// ERRORS FACED
// java.lang.IllegalArgumentException: Invalid return type for async method (only Future and void supported):
// -->
//public String slowOperation() throws InterruptedException {
//    Thread.sleep(5000);
//    return "Slow operation completed";
//}

//Handle Async Exceptions
//When using async processing, it is important to handle exceptions properly.
//If an exception is thrown in an async method, it will not be propagated to the main thread by default.
//To handle exceptions properly, we can use the @Async annotation in combination with the Future class.
//
//Modify the slowOperation method in the DemoController class to return a Future<String> instead of a String