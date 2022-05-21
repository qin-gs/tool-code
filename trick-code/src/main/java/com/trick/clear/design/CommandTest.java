package com.trick.clear.design;

/**
 * 命令：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开；
 * 电视机遥控器（命令发送者）通过按钮（具体命令）来遥控电视机（命令接收者）
 */
public class CommandTest {

    public static void main(String[] args) {
        // 命令接收者 (类似于服务员)
        Receiver receiver = new Receiver();
        // 创建一个命令 (顾客发出点单命令)
        ConcreteCommand command = new ConcreteCommand(receiver);
        // 将命令传递给调用者，可以有很多命令需要依次调用 (将命令给厨师)
        Invoker invoker = new Invoker(command);
        // 调用者执行命令 (厨师开始做饭)
        invoker.call();
    }

    /**
     * 调用者，可以有很多命令对象
     */
    static class Invoker {
        private Command command;

        public Invoker(Command command) {
            this.command = command;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public void call() {
            System.out.println("调用者执行命令");
            command.execute();
        }
    }

    /**
     * 命令
     */
    interface Command {
        void execute();
    }

    static class ConcreteCommand implements Command {

        private Receiver receiver;

        public ConcreteCommand(Receiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public void execute() {
            receiver.action();
        }
    }

    /**
     * 接收者
     */
    static class Receiver {
        public void action() {
            System.out.println("接收者");
        }
    }
}
