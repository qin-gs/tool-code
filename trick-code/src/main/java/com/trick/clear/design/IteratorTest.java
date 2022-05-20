package com.trick.clear.design;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器：顺序访问一个聚合对象中各个元素，而又不暴露该对象的内部表示
 */
public class IteratorTest<T> {

    public static void main(String[] args) {

        // 一个存储数据的容器
        ConcreteAggregate<Integer> aggregate = new ConcreteAggregate<>();
        aggregate.add(1);
        aggregate.add(2);
        aggregate.add(3);
        aggregate.add(4);

        // 获取容器的迭代器
        Iterator<Integer> iterator = aggregate.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 迭代器接口
     */
    interface Iterator<T> {
        T first();

        T next();

        boolean hasNext();
    }

    /**
     * 迭代器的具体实现
     */
    static class ConcreteIterator<T> implements Iterator<T> {
        private List<T> list;
        private int index = -1;

        public ConcreteIterator(List<T> list) {
            this.list = list;
        }

        @Override
        public T first() {
            index = 0;
            return list.get(index);
        }

        @Override
        public T next() {
            if (hasNext()) {
                return list.get(++index);
            } else {
                return null;
            }
        }

        @Override
        public boolean hasNext() {
            return index < list.size() - 1;
        }
    }

    /**
     * 存储内容的数据结构
     */
    interface Aggregate<T> {
        void add(T t);

        void remove(T t);

        Iterator<T> getIterator();
    }

    /**
     * 具体存储内容
     */
    static class ConcreteAggregate<T> implements Aggregate<T> {
        private List<T> list = new ArrayList<>();

        @Override
        public void add(T t) {
            list.add(t);
        }

        @Override
        public void remove(T t) {
            list.remove(t);
        }

        @Override
        public Iterator<T> getIterator() {
            return new ConcreteIterator<>(list);
        }
    }
}
