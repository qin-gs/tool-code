package com.trick.clear.chain;

import org.springframework.stereotype.Service;

import java.util.Objects;

public abstract class PayHandler {
    protected PayHandler next;

    public abstract void pay(String code);

    public PayHandler getNext() {
        return next;
    }

    public void setNext(PayHandler next) {
        this.next = next;
    }
}

@Service
class AliaPayHandler extends PayHandler {

    @Override
    public void pay(String code) {
        if (Objects.equals(code, "alia")) {
            System.out.println("alipay");
        } else {
            getNext().pay(code);
        }
    }
}

@Service
class WechatPayHandler extends PayHandler {

    @Override
    public void pay(String code) {
        if (Objects.equals(code, "wechat")) {
            System.out.println("wechat pay");
        } else {
            getNext().pay(code);
        }
    }
}

@Service
class JdPayHandler extends PayHandler {

    @Override
    public void pay(String code) {
        if (Objects.equals(code, "jd")) {
            System.out.println("jd pay");
        } else {
            getNext().pay(code);
        }
    }
}

