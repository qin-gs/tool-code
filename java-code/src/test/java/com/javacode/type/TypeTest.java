package com.javacode.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@DisplayName("type")
public class TypeTest {

    @Test
    public void getRealType() {
        // 获取泛型类的实际类型信息
        ParameterizedType parameterizedType = (ParameterizedType) ABService.class.getGenericInterfaces()[0];
        Type[] types = parameterizedType.getActualTypeArguments();
        System.out.println(Arrays.toString(types));
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