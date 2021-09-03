package com.trick.clear.template;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 在接口中定义额外的方法，判断是否符合当前支付方式
 */
@Service
public class PayService implements ApplicationContextAware, InitializingBean {
	private ApplicationContext context;
	private List<IPay> payList;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (Objects.isNull(payList)) {
			payList = new ArrayList<>();
			Map<String, IPay> beansOfType = context.getBeansOfType(IPay.class);
			beansOfType.forEach((key, value) -> payList.add(value));
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public void toPay(String code) {
		payList.stream()
				.filter(iPay -> iPay.support(code))
				.findFirst()
				.ifPresent(IPay::pay);
	}
}
