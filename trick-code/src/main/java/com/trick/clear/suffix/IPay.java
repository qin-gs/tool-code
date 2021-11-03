package com.trick.clear.suffix;

import org.springframework.stereotype.Service;

public interface IPay {
    void pay();
}

@Service
class AliPay implements IPay {

    @Override
    public void pay() {
        System.out.println("alipay");
    }
}

@Service
class WeChatPay implements IPay {

    @Override
    public void pay() {
        System.out.println("wechat pay");
    }
}

@Service
class JdPay implements IPay {

    @Override
    public void pay() {
        System.out.println("jd pay");
    }
}