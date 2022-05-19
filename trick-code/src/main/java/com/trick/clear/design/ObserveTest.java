package com.trick.clear.design;

import java.util.ArrayList;
import java.util.List;

public class ObserveTest {

    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        // 创建三个不同名称的观察者
        AObserve aObserve = new AObserve("q");
        AObserve bObserve = new AObserve("w");
        AObserve cObserve = new AObserve("e");

        // 将观察者放入集合中
        subject.add(aObserve);
        subject.add(bObserve);
        subject.add(cObserve);

        // 修改变量值，会通知到所有的观察者
        subject.setMessage("a new message");
        subject.remove(bObserve);
        subject.setMessage("another message");


    }

    /**
     * 保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法
     */
    static abstract class Subject {
        protected List<Observe> observes = new ArrayList<>();

        public void add(Observe observe) {
            observes.add(observe);
        }

        public void remove(Observe observe) {
            observes.remove(observe);
        }

        public abstract void notifyObserves();
    }

    static class ConcreteSubject extends Subject {
        private String message;

        @Override
        public void notifyObserves() {
            for (Observe observe : observes) {
                observe.update(this.message);
            }
        }

        public void setMessage(String message) {
            this.message = message;
            notifyObserves();
        }
    }

    interface Observe {
        void update(String msg);
    }

    static class AObserve implements Observe {
        private String name;

        public AObserve(String name) {
            this.name = name;
        }

        @Override
        public void update(String msg) {
            System.out.println(name + ": " + msg);
        }
    }

}
