package com.trick.clear.design;

/**
 * 模板方法：定义一个操作中的算法的骨架，而将一些步骤延迟到子类中
 */
public class Template {

    public static void main(String[] args) {
        ConcreteTemplate template = new ConcreteTemplate();
        template.doSomething();
    }
}

abstract class AbsTemplate {

    /**
     * 模板方法，给出了基本骨架
     */
    public void doSomething() {
        first();
        second();
        last();
    }

    protected void last() {
        System.out.println("默认 last");
    }

    abstract protected void second();

    protected void first() {
        System.out.println("默认 first");
    }

}

class ConcreteTemplate extends AbsTemplate {

    @Override
    protected void second() {
        System.out.println("具体实现类的 second");
    }
}