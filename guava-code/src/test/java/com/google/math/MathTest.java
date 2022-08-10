package com.google.math;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import org.junit.jupiter.api.Test;

public class MathTest {

    @Test
    public void test() {
        int pow = IntMath.pow(2, 3);
        System.out.println(pow);

        int i = IntMath.checkedAdd(Integer.MAX_VALUE, -2);
        System.out.println(i);

        // 最大公约数
        int gcd = IntMath.gcd(24, 91);
        System.out.println(gcd);

        DoubleMath.isMathematicalInteger(2.0);
        DoubleMath.log2(8);
    }
}
