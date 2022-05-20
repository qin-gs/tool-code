package com.trick.clear.design;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 */
public class CompositeTest {

    public static void main(String[] args) {
        Composite composite1 = new Composite();
        Composite composite2 = new Composite();

        Component leaf1 = new Leaf("1");
        Component leaf2 = new Leaf("2");
        Component leaf3 = new Leaf("3");

        composite1.add(leaf1);
        composite1.add(composite2);
        composite2.add(leaf2);
        composite2.add(leaf3);

        composite1.operation();

    }

    /**
     * 为树叶构件和树枝构件声明公共接口
     */
    static interface Component {

        void add(Component component);

        void remove(Component component);

        Component getChild(int i);

        void operation();

    }

    @Data
    static class Leaf implements Component {
        private String name;

        public Leaf(String name) {
            this.name = name;
        }

        @Override
        public void add(Component component) {
            throw new UnsupportedOperationException("叶子节点不支持新增子节点");
        }

        @Override
        public void remove(Component component) {
            throw new UnsupportedOperationException("叶子节点不支持移除子节点");
        }

        @Override
        public Component getChild(int i) {
            return null;
        }

        @Override
        public void operation() {
            System.out.println("叶子：" + name);
        }
    }

    static class Composite implements Component {
        private List<Component> children = new ArrayList<>();

        @Override
        public void add(Component component) {
            children.add(component);
        }

        @Override
        public void remove(Component component) {
            children.remove(component);
        }

        @Override
        public Component getChild(int i) {
            return children.get(i);
        }

        @Override
        public void operation() {
            for (Component child : children) {
                child.operation();
            }
        }
    }
}
