package com.trick.clear.design;

import lombok.Data;

/**
 * 状态
 */
public class StateTest {

    public static void main(String[] args) {
        Work work = new Work();

        // 对象的内在状态改变时改变其行为
        work.setHour(8);
        work.handle();
        work.setHour(15);
        work.handle();
        work.setHour(21);
        work.handle();
    }

    static abstract class State {
        public abstract void doSomething(Work w);
    }

    static class MorningState extends State {
        @Override
        public void doSomething(Work w) {
            if (w.getHour() < 12) {
                System.out.println("上午");
            } else {
                w.setState(new NoonState());
                w.handle();
            }
        }
    }

    static class NoonState extends State {
        @Override
        public void doSomething(Work w) {
            if (w.getHour() < 18) {
                System.out.println("下午");
            } else {
                w.setState(new EveningState());
                w.handle();
            }
        }
    }

    static class EveningState extends State {
        @Override
        public void doSomething(Work w) {
            System.out.println("晚上");
        }
    }

    @Data
    static class Work {
        private State state;
        private int hour;
        private boolean isFinished;

        public Work() {
            this.state = new MorningState();
        }

        public void handle() {
            state.doSomething(this);
        }
    }

}
