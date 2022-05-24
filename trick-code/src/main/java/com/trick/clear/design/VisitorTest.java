package com.trick.clear.design;

import java.util.ArrayList;
import java.util.List;

public class VisitorTest {

    public static void main(String[] args) {

        ObjectStructure os = new ObjectStructure();
        os.add(new ConcreteElementA());
        os.add(new ConcreteElementB());

        ConcreteVisitorA visitorA = new ConcreteVisitorA();
        os.accept(visitorA);

        ConcreteVisitorB visitorB = new ConcreteVisitorB();
        os.accept(visitorB);
    }

    /**
     * 访问者：为每个具体元素对应一个访问操作
     */
    interface Visitor {
        void visit(ConcreteElementA a);

        void visit(ConcreteElementB b);
    }

    /**
     * 具体访问者：实现访问操作
     */
    static class ConcreteVisitorA implements Visitor {
        @Override
        public void visit(ConcreteElementA a) {
            System.out.println("具体访问者 a 访问 " + a.operationA());
        }

        @Override
        public void visit(ConcreteElementB b) {
            System.out.println("具体访问者 a 操作 " + b.operationB());
        }
    }

    static class ConcreteVisitorB implements Visitor {
        @Override
        public void visit(ConcreteElementA a) {
            System.out.println("具体访问者 b 访问 " + a.operationA());
        }

        @Override
        public void visit(ConcreteElementB b) {
            System.out.println("具体访问者 b 操作 " + b.operationB());
        }
    }

    /**
     * 抽象元素：
     */
    interface Element {
        void accept(Visitor visitor);
    }

    static class ConcreteElementA implements Element {
        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public String operationA() {
            return "具体元素 a 的操作";
        }
    }

    static class ConcreteElementB implements Element {
        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public String operationB() {
            return "具体元素 b 的操作";
        }
    }

    /**
     * 对象结构：包含元素角色的容器
     */
    static class ObjectStructure {
        private List<Element> elements = new ArrayList<>();

        public void accept(Visitor visitor) {
            for (Element element : elements) {
                element.accept(visitor);
            }
        }

        public void add(Element element) {
            elements.add(element);
        }

        public void remove(Element element) {
            elements.remove(element);
        }
    }


}
