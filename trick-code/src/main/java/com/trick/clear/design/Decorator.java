package com.trick.clear.design;

/**
 * 装饰器：被装饰的对象 和 装饰器本身 要实现同一个接口
 */
public class Decorator {

    public static void main(String[] args) {

        Circle circle = new Circle();
        RedShapeDecorator redCircle = new RedShapeDecorator(circle);

        // 没有装饰之前
        circle.draw();
        // 装饰之后
        redCircle.draw();
    }
}

interface Shape {
    void draw();
}

class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("rectangle");
    }
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("circle");
    }
}

class ShapeDecorator implements Shape {

    protected Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        shape.draw();
    }
}

class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        addColor(shape);
    }

    private void addColor(Shape shape) {
        System.out.println("red");
    }
}
