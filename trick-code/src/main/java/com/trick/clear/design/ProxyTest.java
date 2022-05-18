package com.trick.clear.design;

/**
 * 代理模式：为其他对象提供一种代理以控制对这个对象的访问
 * 代理类 和 被代理类 要实现同一个接口
 */
public class ProxyTest {

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.request();
    }
}

interface Subject {
    void request();
}

class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("访问真实主题方法");
    }
}

class Proxy implements Subject {

    private RealSubject subject;

    @Override
    public void request() {
        if (subject == null) {
            subject = new RealSubject();
        }

        beforeRequest();
        subject.request();
        afterRequest();

    }

    private void beforeRequest() {
        System.out.println("调用 request 方法前");
    }

    private void afterRequest() {
        System.out.println("调用 request 方法后");
    }

}


