package com.apache.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 简单的对象工厂，没有其他复杂操作(激活，钝化等)
 */
public class SimplePooledObjectFactory extends BasePooledObjectFactory<User> {
    @Override
    public User create() throws Exception {
        String name = "test-" + ThreadLocalRandom.current().nextInt(100);
        return new User(name);
    }

    /**
     * 使用默认的池化对象包装对象
     */
    @Override
    public PooledObject<User> wrap(User obj) {
        return new DefaultPooledObject<>(obj);
    }
}
