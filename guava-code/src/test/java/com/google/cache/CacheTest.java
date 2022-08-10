package com.google.cache;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.*;

public class CacheTest {

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Test
    public void test() throws ExecutionException {
        LoadingCache<String, User> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 添加被移除时的监听器
                .removalListener(new RemovalListener<String, User>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, User> notification) {
                        System.out.println("removalListener: " + notification.getKey() + " " + notification.getValue());
                        System.out.println(notification.getCause());
                    }
                })
                // 统计
                .recordStats()
                // 通过定时刷新让缓存项保持可用
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public User load(String key) throws Exception {
                                return User.Builder.anUser().name(key).build();
                            }

                            @Override
                            public ListenableFuture<User> reload(String key, User oldValue) throws Exception {
                                // 如果不需要刷新
                                // return Futures.immediateFuture(oldValue);

                                // 如果需要刷新，使用异步方式
                                ListenableFutureTask<User> task = ListenableFutureTask.create(new Callable<User>() {
                                    @Override
                                    public User call() throws Exception {
                                        return User.Builder.anUser().name(key).build();
                                    }
                                });
                                executor.execute(task);
                                return task;
                            }
                        }
                );

        cache.put("1", User.Builder.anUser().name("1").build());
        cache.put("2", User.Builder.anUser().name("2").build());
        cache.put("3", User.Builder.anUser().name("3").build());
        // 清除缓存
        cache.invalidate("2");


        System.out.println(cache.getUnchecked("4"));
        System.out.println(cache.getAll(Arrays.asList("1", "2", "3")));

        // 存在就返回，不存在计算 callable 然后缓存
        User user = cache.get("4", () -> User.Builder.anUser().name("4").build());

        // 回收：
        // 基于容量 的回收策略：当缓存达到最大容量时，将最近最少使用的缓存项移除。(可以指定权重)
        // 定时回收：expireAfterWrite (没有被创建或覆盖), expireAfterAccess (没有被读写)
        // 引用回收：通过使用弱引用的键、或弱引用的值、或软引用的值，Guava Cache 可以把缓存设置为允许垃圾回收
    }
}
