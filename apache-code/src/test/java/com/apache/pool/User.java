package com.apache.pool;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 模拟一个复杂对象的创建过程
 */
public class User {
    private String name;

    public User(String name) {
        try {
            long start = Instant.now().toEpochMilli();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            Thread.sleep(2000 + random.nextInt(2000));
            long end = Instant.now().toEpochMilli();
            System.out.println("创建对象时间 " + (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
