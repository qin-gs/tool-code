package com.springcode.tx;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;

/**
 * Spring中的事务问题
 */
public class TransactionTest {
    /**
     * 事务不生效问题 @Transaction
     * <p>
     * 1. 事务方法的访问权限必须是public
     * {@link AbstractFallbackTransactionAttributeSource#computeTransactionAttribute(Method, Class)}中进行了判断
     * 2. 事务方法不能为 final (不能创建动态代理(无法继承))
     * 3. 方法内部调用 (动态代理的方法中用this调用目标类的其他方法)
     * 1. 注入自己
     * 2. {@link AopContext#currentProxy()} 获取代理对象
     * 4. 代理方法所在对象需要被spring管理(@Component)，数据库要支持事务(myisam)，spring开启事务({@link EnableTransactionManagement})
     * 5. 需要是同一个线程
     */
    @Transactional(rollbackFor = Exception.class)
    public void test() {
    }

    /**
     * 编程式事务
     */
    @Autowired
    private TransactionTemplate template;

    public void testTx() {
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                return null;
            }
        });
    }
}
