package com.effective.chapter05;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

public class Stack<E> {
    private final E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public Stack() {
        // 这里会有一个警告
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) throw new EmptyStackException();
        E result = elements[--size];
        // 防止内存泄漏，需要置为 null
        elements[size] = null; // Eliminate obsolete reference return result;
        return result;
    }

    public void ensureCapacity() {
        if (size > DEFAULT_INITIAL_CAPACITY) {
            throw new RuntimeException("数组已满");
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * src 是生产者，只能往外取数据
     */
    public void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    /**
     * dest 是消费者，只能往里面放数据
     */
    public void popAll(Collection<? super E> dest) {
        while (!isEmpty()) {
            dest.add(pop());
        }
    }

    @Override
    public String toString() {
        return "Stack{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
