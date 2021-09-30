package com.apache.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * 对象池测试
 * <p>
 * 一种享元模式的实现，主要有三个角色：
 * <pre>
 *   1. PooledObject: 池化对象(包装创建出来的对象，提供一些附加功能)
 *   2. PooledObjectFactory: 对象工厂(创建对象)
 *   3. ObjectPool: 对象池接口(管理对象)
 * </pre>
 */
@DisplayName("apache pool 测试类")
public class PoolTest {

    @Test
    public void test() throws Exception {
        GenericObjectPoolConfig<User> config = getPoolConfig();
        ComplexPooledObjectFactory factory = new ComplexPooledObjectFactory();
        GenericObjectPool<User> pool = new GenericObjectPool<>(factory, config);

        // 申请，归还对象
        User user1 = pool.borrowObject();
        System.out.println(user1.getName());
        pool.returnObject(user1);

        // 这次获取到的是第一次创建的
        User user2 = pool.borrowObject();
        System.out.println(user2.getName());

        // 由于之前的没有归还，这里会创建第二个对象
        User user3 = pool.borrowObject();
        System.out.println(user3.getName());

        // 归还对象应放到finally中
        pool.returnObject(user2);
        pool.returnObject(user3);


    }

    private GenericObjectPoolConfig<User> getPoolConfig() {
        GenericObjectPoolConfig<User> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(5); // 最大空闲对象数量，超过该值的空闲对象会被销毁
        config.setMaxTotal(20); // 最大对象数量，包含空闲的和正在使用的
        config.setMinIdle(2); // 最小实例数(对象池中最少保留对象的个数)
        config.setMaxWaitMillis(3 * 60 * 1000); // 连接池资源耗尽时，调用者的最大阻塞时间，超出将抛出异常
        config.setMinEvictableIdleTime(Duration.ofMillis(3 * 60 * 1000)); // 空闲的最小时间，达到此值后空闲连接将可能会被移除
        config.setBlockWhenExhausted(true); // 对象池满了，是否阻塞获取(false: 获取不到直接抛异常)
        config.setTestOnBorrow(true); // 借用对象后是否验证
        config.setTestOnCreate(false); // 创建对象后是否验证
        config.setTestOnReturn(false); // 归还对象后是否验证
        config.setTestWhileIdle(false); // 定时检测对象时是否验证
        config.setTimeBetweenEvictionRuns(Duration.ofMillis(30 * 1000)); // 定时检查淘汰对于对象，启用另外的线程处理
        config.setJmxEnabled(false); // jmx监控
        return config;
    }

}

