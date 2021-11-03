package com.trick.clear.suffix;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 根据名称获取bean对象
 */
@Service
public class PayService implements ApplicationContextAware {
    private ApplicationContext context;
    private static final String SUFFIX = "Pay";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void pay(String code) {
        ((IPay) context.getBean(getBeanName(code))).pay();
    }

    private String getBeanName(String code) {
        return code + SUFFIX;
    }
}
