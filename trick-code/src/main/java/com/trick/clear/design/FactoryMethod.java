package com.trick.clear.design;

/**
 * 简单工厂：创建一个类通过switch case 创建对应的对象 (这里不符合 开放封闭 原则，添加新操作时候需要添加 case)；
 * 工厂方法：定义一个用于创建对象的接口，让子类决定实例化哪一个类；
 */
public class FactoryMethod {

    public static void main(String[] args) {
        // 从不同的工厂中拿到不同的产品
        Product a = new AFactory().getProduct();
        a.show();

        Product b = new BFactory().getProduct();
        b.show();
    }
}

/**
 * 产品接口
 */
interface Product {
    void show();
}

class AProduct implements Product {
    @Override
    public void show() {
        System.out.println("a product");
    }
}

class BProduct implements Product {
    @Override
    public void show() {
        System.out.println("b product");
    }
}

/**
 * 工厂接口
 */
interface Factory {
    Product getProduct();
}

class AFactory implements Factory {
    @Override
    public Product getProduct() {
        return new AProduct();
    }
}

class BFactory implements Factory {
    @Override
    public Product getProduct() {
        return new BProduct();
    }
}
