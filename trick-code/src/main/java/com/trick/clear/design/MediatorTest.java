package com.trick.clear.design;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者：定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互
 */
public class MediatorTest {

    public static void main(String[] args) {

        Mediator mediator = new ConcreteMediator();
        Colleague a = new AColleague();
        Colleague b = new BColleague();
        mediator.register(a);
        mediator.register(b);

        // a 发出，b 会收到
        a.send();

        // b 发出，a 会收到
        b.send();
    }

    /**
     * 中介
     */
    abstract static class Mediator {
        /**
         * 发送
         */
        abstract public void register(Colleague colleague);

        /**
         * 转发
         */
        public abstract void replay(Colleague colleague);
    }

    static class ConcreteMediator extends Mediator {
        List<Colleague> colleagues = new ArrayList<>();

        @Override
        public void register(Colleague colleague) {
            if (!colleagues.contains(colleague)) {
                colleague.setMediator(this);
                colleagues.add(colleague);
            }
        }

        /**
         * 转发给除自己外其他人
         */
        @Override
        public void replay(Colleague colleague) {
            for (Colleague col : colleagues) {
                if (!col.equals(colleague)) {
                    col.receive();
                }
            }
        }
    }

    static abstract class Colleague {
        protected Mediator mediator;

        public void setMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        public abstract void receive();

        public abstract void send();
    }

    static class AColleague extends Colleague {

        @Override
        public void receive() {
            System.out.println("a 收到");
        }

        @Override
        public void send() {
            System.out.println("a 发出");
            // 中介转发
            mediator.replay(this);
        }
    }

    static class BColleague extends Colleague {

        @Override
        public void receive() {
            System.out.println("b 收到");
        }

        @Override
        public void send() {
            System.out.println("b 发出");
            // 中介转发
            mediator.replay(this);
        }
    }
}
