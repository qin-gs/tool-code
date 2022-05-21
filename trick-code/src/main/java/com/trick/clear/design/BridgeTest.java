package com.trick.clear.design;

/**
 * 桥接：将抽象(抽象类)与实现(接口)分离，使它们可以独立变化；
 * 抽象类中通过构造函数传入一个接口，通过另一个方法组合两者
 */
public class BridgeTest {

    public static void main(String[] args) {

        // 创建抽象 和 实现
        ConcreteImplementorA imple = new ConcreteImplementorA();
        // 将实现通过构造函数传给 抽象
        RefinedAbstraction abs = new RefinedAbstraction(imple);
        abs.operation();
    }

    /**
     * 实现化角色
     */
    interface Implementor {
        void OperationImpl();
    }

    static class ConcreteImplementorA implements Implementor {
        @Override
        public void OperationImpl() {
            System.out.println("实现 被访问");
        }
    }

    /**
     * 抽象
     */
    static abstract class Abstraction {
        protected Implementor imple;

        /**
         * 通过构造函数将实现传过来
         */
        public Abstraction(Implementor imple) {
            this.imple = imple;
        }

        /**
         * 通过这个方法组合实现 和 抽象
         */
        abstract void operation();
    }

    static class RefinedAbstraction extends Abstraction {
        public RefinedAbstraction(Implementor imple) {
            super(imple);
        }

        @Override
        void operation() {
            System.out.println("抽象 被访问");
            imple.OperationImpl();
        }
    }

}
