package com.apache.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 复杂对象工厂
 * 必须是线程安全的
 * 需要一个新实例时，就调用 makeObject 方法。
 * 需要借用对象时会调用 activateObject 方法激活对象，并且根据配置情况决定是否验证对象有效性，通过 validateObject 方法验证。
 * 归还对象时会调用 passivateObject 方法钝化对象。
 * 需要销毁对象时候调用 destroyObject 方法。
 */
public class ComplexPooledObjectFactory implements PooledObjectFactory<User> {

    /**
     * 创建对象
     */
    @Override
    public PooledObject<User> makeObject() throws Exception {
        String name = "test-" + ThreadLocalRandom.current().nextInt(100);
        // 池化对象，用于包装实际的对象，提供一些附件的功能。
        // 如 Commons-Pool 自带的 DefaultPooledObject 会记录对象的创建时间，借用时间，归还时间，对象状态等
        return new DefaultPooledObject<>(new User(name));
    }

    /**
     * 验证对象状态是否正常可用
     */
    @Override
    public boolean validateObject(PooledObject<User> p) {
        return true;
    }

    /**
     * 集合对象使其可用
     */
    @Override
    public void activateObject(PooledObject<User> p) throws Exception {
        // 借用对象时初始化一些状态
    }

    /**
     * 钝化对象，使其不可用
     */
    @Override
    public void passivateObject(PooledObject<User> p) throws Exception {
        // 归还对象时调用
    }

    /**
     * 销毁对象(清空、空闲对象大于配置时会销毁)，释放资源
     */
    @Override
    public void destroyObject(PooledObject<User> p) throws Exception {

    }
}
