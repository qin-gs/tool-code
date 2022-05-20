package com.trick.clear.design;

import lombok.Data;

/**
 * 备忘录：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态
 */
public class MementoTest {

    public static void main(String[] args) {
        // 发起人
        Originator originator = new Originator();
        // 管理者
        Caretaker caretaker = new Caretaker();

        // 设置初始状态，创建一个备忘录
        originator.setState("first");
        caretaker.setMemento(originator.createMemento());

        // 修改状态
        originator.setState("second");

        // 从备忘录中恢复
        originator.resetMemento(caretaker.getMemento());

        System.out.println("originator.getState() = " + originator.getState());
    }

    /**
     * 备忘录：存储发起人的状态
     */
    @Data
    static class Memento {
        private String state;

        public Memento(String state) {
            this.state = state;
        }
    }

    /**
     * 发起人：提供创建备忘录 和 恢复功能
     */
    @Data
    static class Originator {
        private String state;

        /**
         * 创建
         */
        public Memento createMemento() {
            return new Memento(state);
        }

        /**
         * 恢复
         */
        public void resetMemento(Memento memento) {
            this.setState(memento.getState());
        }
    }

    /**
     * 管理者：提供获取 和 保存功能，不能对备忘录的内容进行修改
     */
    @Data
    static class Caretaker {
        private Memento memento;
    }
}
