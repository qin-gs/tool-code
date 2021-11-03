package com.trick.clear.before;

import java.util.Objects;

public class ClearIf {
    private final AliPay aliPay = new AliPay();
    private final WeChatPay weChatPay = new WeChatPay();
    private final JdPay jdPay = new JdPay();

    public void toPay(String code) {
        if (Objects.equals("alia", code)) {
            aliPay.pay();
        } else if (Objects.equals("wechat", code)) {
            weChatPay.pay();
        } else if (Objects.equals("jd", code)) {
            jdPay.pay();
        } else {
            System.out.println("找不到支付方式");
        }
    }
}
