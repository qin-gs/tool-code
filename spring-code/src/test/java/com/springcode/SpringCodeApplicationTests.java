package com.springcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@SpringBootTest
class SpringCodeApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        org.springframework.stereotype.Service annotation = AnnotationUtils.findAnnotation(ABService.class, org.springframework.stereotype.Service.class);
        Component a = AnnotationUtils.getAnnotation(annotation, Component.class);
    }

}

interface Service<M, N> {
}

class ABService implements Service<A, B> {
}

class CDService implements Service<C, D> {
}

class A {
}

class B {
}

class C {
}

class D {
}