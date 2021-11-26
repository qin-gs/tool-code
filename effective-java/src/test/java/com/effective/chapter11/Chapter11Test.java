package com.effective.chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@DisplayName("并发")
public class Chapter11Test {

    private boolean stop = false;

    /**
     * 78. 同步访问共享的可变数据
     */
    @Test
    public void test78() throws InterruptedException {
        // 同步
        // 同步可以阻止一个线程看到对象处于不一致的状态
        // 还可以保证进入同步方法或同步代码块的每个线程，都能看到由同一个锁保护的之前所有的修改效果

        // 不使用 Thread.stop 方法

        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
    }
}
