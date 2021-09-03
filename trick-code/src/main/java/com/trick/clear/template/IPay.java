package com.trick.clear.template;

import org.springframework.stereotype.Service;

import java.util.Objects;

public interface IPay {
	void pay();

	boolean support(String code);
}

@Service
class AliPay implements IPay {

	@Override
	public void pay() {
		System.out.println("alipay");
	}

	@Override
	public boolean support(String code) {
		return Objects.equals("alia", code);
	}
}

@Service
class WeChatPay implements IPay {

	@Override
	public void pay() {
		System.out.println("wechat pay");
	}

	@Override
	public boolean support(String code) {
		return Objects.equals("wechat", code);
	}
}

@Service
class JdPay implements IPay {

	@Override
	public void pay() {
		System.out.println("jd pay");
	}

	@Override
	public boolean support(String code) {
		return Objects.equals("jd", code);
	}
}