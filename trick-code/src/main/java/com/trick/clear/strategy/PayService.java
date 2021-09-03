package com.trick.clear.strategy;

import org.springframework.stereotype.Service;

/**
 * 在启动的时候，将支付方式注册到工厂
 */
@Service
public class PayService {

	public void toPay(String code) {
		PayStrategyFactory.getPay(code).pay();
	}
}
