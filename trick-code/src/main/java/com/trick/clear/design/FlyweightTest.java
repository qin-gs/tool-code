package com.trick.clear.design;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元：运用共享技术来有效地支持大量细粒度对象的复用；为了使对象可以共享，需要将一些不能共享的状态外部化
 */
public class FlyweightTest {

    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        // 只会创建 3 个对象
        Flyweight a = factory.getFlyweight("a");
        Flyweight b = factory.getFlyweight("b");
        Flyweight c = factory.getFlyweight("c");
        Flyweight aa = factory.getFlyweight("a");
        Flyweight cc = factory.getFlyweight("c");

        a.operation(new UnsharedConcreteFlyweight("第 1 次调用 a"));
        a.operation(new UnsharedConcreteFlyweight("第 2 次调用 a"));
        b.operation(new UnsharedConcreteFlyweight("第 1 次调用 b"));

    }

    /**
     * 抽象的享元角色，包含了享元方法，非享元的外部状态以参数的形式传入
     */
    interface Flyweight {
        void operation(UnsharedConcreteFlyweight state);
    }

    /**
     * 具体的享元角色
     */
    static class ConcreteFlyweight implements Flyweight {
        private String key;

        public ConcreteFlyweight(String key) {
            this.key = key;
            System.out.println("享元 " + key + " 已被创建");
        }

        @Override
        public void operation(UnsharedConcreteFlyweight state) {
            System.out.println("具体享元 " + key + " 被调用");
            System.out.println("非享元信息是 " + state.getInfo());
        }
    }

    /**
     * 非共享的外部信息 (变化的(例如：用户身份))
     */
    static class UnsharedConcreteFlyweight {
        private String info;

        public UnsharedConcreteFlyweight(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 享元工厂
     */
    static class FlyweightFactory {
        private Map<String, Flyweight> flyweights = new HashMap<>();

        public Flyweight getFlyweight(String key) {
            Flyweight flyweight = flyweights.get(key);
            if (flyweight == null) {
                flyweight = new ConcreteFlyweight(key);
                flyweights.put(key, flyweight);
            }

            return flyweight;
        }
    }
}
