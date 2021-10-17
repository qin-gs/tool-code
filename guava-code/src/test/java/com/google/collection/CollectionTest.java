package com.google.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

@DisplayName("不可变集合")
public class CollectionTest {

    @Test
    public void test() {
        Collections.unmodifiableCollection(new ArrayList<>());
    }
}
