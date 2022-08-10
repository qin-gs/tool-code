package com.google.concurrency;

import com.google.common.util.concurrent.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FutureTest {

    @Test
    public void future() {
        // 创建线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        // 提交任务
        ListenableFuture<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return "hello world";
            }
        });
        // 将任务交给线程池，添加回调
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        }, service);


        AsyncFunction<String, String> function = new AsyncFunction<>() {
            @Override
            public ListenableFuture<String> apply(String input) throws Exception {
                return Futures.immediateFuture(input);
            }
        };


    }
}
