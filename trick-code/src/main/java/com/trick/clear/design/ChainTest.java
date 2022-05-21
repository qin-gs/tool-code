package com.trick.clear.design;

/**
 * 责任链：为了避免请求发送者与多个请求处理者耦合在一起，于是将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；
 * 当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止
 */
public class ChainTest {

    public static void main(String[] args) {

        // 创建两个具体的处理类
        Handler a = new AHandler();
        Handler b = new BHandler();
        // 配置责任链的顺序
        a.setNext(b);

        // 从链中找到对应的处理类进行处理
        a.handleRequest("b");
    }

    static abstract class Handler {
        private Handler next;

        public void setNext(Handler next) {
            this.next = next;
        }

        public Handler getNext() {
            return next;
        }

        public abstract void handleRequest(String request);
    }

    static class AHandler extends Handler {
        @Override
        public void handleRequest(String request) {
            if (request.equals("a")) {
                System.out.println("a 处理该请求");
            } else {
                if (getNext() != null) {
                    getNext().handleRequest(request);
                } else {
                    System.out.println("没有人处理请求");
                }
            }
        }
    }

    static class BHandler extends Handler {
        @Override
        public void handleRequest(String request) {
            if (request.equals("b")) {
                System.out.println("b 处理该请求");
            } else {
                if (getNext() != null) {
                    getNext().handleRequest(request);
                } else {
                    System.out.println("没有人处理请求");
                }
            }
        }
    }
}
