package com.trick.clear.design;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 创建一些复杂的对象，这些对象内部构建间的建造顺序通常是稳定的，但对象内部的构建通常面临着复杂的变化；
 * 建造者：包含创建产品各个子部件的抽象方法；
 * 产品：包含多个组成部件的复杂对象；
 * 指挥者：调用建造者中的方法完成复杂对象的创建；
 */
public class BuilderTest {

    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.build();
        product.show();
    }


    @Setter
    @Getter
    @ToString
    static
    class Product {
        private String partA;
        private String partB;
        private String partC;

        public void show() {
            System.out.println(this);
        }
    }

    abstract static class Builder {
        protected Product product = new Product();

        public abstract void buildPartA();

        public abstract void buildPartB();

        public abstract void buildPartC();

        public Product getProduct() {
            return product;
        }
    }

    static class ConcreteBuilder extends Builder {
        @Override
        public void buildPartA() {
            product.setPartA("part A");
        }

        @Override
        public void buildPartB() {
            product.setPartB("part b");
        }

        @Override
        public void buildPartC() {
            product.setPartC("part c");
        }
    }

    static class Director {
        private Builder builder;

        public Director(Builder builder) {
            this.builder = builder;
        }

        public Product build() {
            builder.buildPartA();
            builder.buildPartB();
            builder.buildPartC();
            return builder.getProduct();
        }
    }
}
