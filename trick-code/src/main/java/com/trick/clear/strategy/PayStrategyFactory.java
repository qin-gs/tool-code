package com.trick.clear.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PayStrategyFactory {
    private static final Map<String, IPay> PAY_REGISTERS = new HashMap<>();

    public static void register(String code, IPay iPay) {
        if (StringUtils.isNotEmpty(code)) {
            PAY_REGISTERS.put(code, iPay);
        }
    }

    public static IPay getPay(String code) {
        return PAY_REGISTERS.get(code);
    }
}
