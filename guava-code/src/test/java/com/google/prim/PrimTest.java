package com.google.prim;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInts;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PrimTest {

    @Test
    public void test() {
        List<Integer> list = Ints.asList(1, 2, 3);

        boolean contains = Iterables.contains(list, 4);
        System.out.println(contains);
    }
}
