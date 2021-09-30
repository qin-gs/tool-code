package com.apache.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

/**
 * 支持key-value的形式
 */
public class ComplexKeyedPooledObjectFactory implements KeyedPooledObjectFactory<String, User> {

    @Override
    public void activateObject(String key, PooledObject<User> p) throws Exception {

    }

    @Override
    public void destroyObject(String key, PooledObject<User> p) throws Exception {

    }

    @Override
    public PooledObject<User> makeObject(String key) throws Exception {
        return null;
    }

    @Override
    public void passivateObject(String key, PooledObject<User> p) throws Exception {

    }

    @Override
    public boolean validateObject(String key, PooledObject<User> p) {
        return false;
    }
}
