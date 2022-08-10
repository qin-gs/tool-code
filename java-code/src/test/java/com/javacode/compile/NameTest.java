package com.javacode.compile;

/**
 * javac -encoding UTF-8 com/javacode/compile/NameCheck.java
 * javac -encoding UTF-8 com/javacode/compile/NameCheckProcessor.java
 * javac -encoding UTF-8 -processor com.javacode.compile.NameCheckProcessor com/javacode/compile/NameTest.java
 */
public class NameTest {

    enum colors {
        red, blue, green
    }

    static final int _FORTY_TWO = 42;
    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BAD_NAME_CODE() {
        return;
    }

    public void NOTcamelCASEmethodNAME() {
        return;
    }
}
