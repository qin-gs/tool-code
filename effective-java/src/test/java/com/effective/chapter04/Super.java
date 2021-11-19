package com.effective.chapter04;

import java.time.Instant;

public class Super {
    public Super() {
        this.overrideMe(); // 这里会输出 null
    }

    public void overrideMe() {

    }
}

class Sub extends Super {
    private final Instant instant;

    public Sub() {
        instant = Instant.now();
    }

    @Override
    public void overrideMe() {
        System.out.println(instant);
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
    }
}