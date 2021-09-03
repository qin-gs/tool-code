package com.trick.clear.annotation;

import org.springframework.stereotype.Service;

public interface IPay {
	void pay();
}

@Service
@PayCode(value = "alia", name = "支付宝支付")
class AliPay implements IPay {

	@Override
	public void pay() {
		System.out.println("alipay");
	}
}

@Service
@PayCode(value = "wechat", name = "微信支付")
class WeChatPay implements IPay {

	@Override
	public void pay() {
		System.out.println("wechat pay");
	}
}

@Service
@PayCode(value = "jd", name = "京东支付")
class JdPay implements IPay {

	@Override
	public void pay() {
		System.out.println("jd pay");
	}
}