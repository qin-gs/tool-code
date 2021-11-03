package com.trick.clear.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 在所有的支付类中添加{@link PayCode}注解
 * 获取所有被注解的bean对象，在容器刷新的时候全部放进去
 */
@Service
public class PayService implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, IPay> payMap;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(PayCode.class);
        if (beansWithAnnotation != null) {
            payMap = new HashMap<>();
            beansWithAnnotation.forEach((key, value) -> {
                String type = value.getClass().getAnnotation(PayCode.class).value();
                payMap.put(type, (IPay) value);
            });
        }
    }

    public void pay(String code) {
        payMap.get(code).pay();
    }
}
