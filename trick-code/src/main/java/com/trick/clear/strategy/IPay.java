package com.trick.clear.strategy;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

public interface IPay {
    void pay();
}

@Service
class AliPay implements IPay {

    @PostConstruct
    public void init() {
        PayStrategyFactory.register("alia", this);
    }

    @Override
    public void pay() {
        System.out.println("alipay");
    }

}

@Service
class WeChatPay implements IPay {

    @PostConstruct
    public void init() {
        PayStrategyFactory.register("wechat", this);
    }

    @Override
    public void pay() {
        System.out.println("wechat pay");
    }
}

@Service
class JdPay implements IPay {

    @PostConstruct
    public void init() {
        PayStrategyFactory.register("jd", this);
    }

    @Override
    public void pay() {
        System.out.println("jd pay");
    }
}