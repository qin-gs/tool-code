package com.trick.clear.design;

/**
 * 适配器
 */
public class AdapterTest {

    public static void main(String[] args) {
        Target target = new ObjectAdapter();
        // 调用的是客户端的接口，实际返回是配置中的逻辑
        target.request();
    }

    /**
     * 这是客户期待的接口
     */
    interface Target {
        void request();
    }

    /**
     * 适配器：
     * 实现客户需要的接口，使用现有的类重写接口中的方法
     */
    static class ObjectAdapter implements Target {

        private Adaptee adaptee;

        public ObjectAdapter() {
            this.adaptee = new Adaptee();
        }

        @Override
        public void request() {
            adaptee.specificRequest();
        }
    }

    /**
     * 需要适配的类
     */
    static class Adaptee {
        public void specificRequest() {
            System.out.println("适配器中的业务逻辑");
        }
    }
}
