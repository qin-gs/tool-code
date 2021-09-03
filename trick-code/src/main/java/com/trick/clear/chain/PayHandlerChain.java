package com.trick.clear.chain;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PayHandlerChain implements ApplicationContextAware, InitializingBean {
	private ApplicationContext context;
	private PayHandler handler;

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, PayHandler> beansOfType = context.getBeansOfType(PayHandler.class);
		if (MapUtils.isEmpty(beansOfType)) {
			return;
		}
		List<PayHandler> handlers = new ArrayList<>(beansOfType.values());
		for (int i = 0; i < handlers.size(); i++) {
			PayHandler handler = handlers.get(i);
			if (i != handlers.size() - 1) {
				handler.setNext(handlers.get(i + 1));
			}
		}
		handler = handlers.get(0);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public void pay(String code) {
		handler.pay(code);
	}
}
