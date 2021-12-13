package com.javacode.xml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * jdom 解析 xml
 * <pre>
 *   优点
 *     实现了许多工具类，比起直接使用DOM的API，简便了XML的解析过程
 *     加入了大量java集合，方便java程序员进行开发
 *   缺点
 *     不是面向接口设计，没有较好的灵活性
 *     优化并不是很好，性能没有那么优异
 * </pre>
 */
@DisplayName("jdom 解析 xml")
public class JdomParser {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        List<Map<String, String>> result = new ArrayList<>();
        // 创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        // 创建输入流
        String file = Objects.requireNonNull(DomParser.class.getResource("/demo.xml")).getFile();
        InputStream is = new FileInputStream(file);
        // 将输入流加载到build中
        Document document = saxBuilder.build(is);
        // 获取根节点
        Element rootElement = document.getRootElement();
        // 获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            List<Attribute> attributes = child.getAttributes();
            // 获取节点上的属性
            for (Attribute attr : attributes) {
                System.out.println(attr.getName() + ":" + attr.getValue());
            }
            // 获取子节点
            List<Element> childrenList = child.getChildren();
            Map<String, String> map = new HashMap<>(4);
            for (Element o : childrenList) {
                String name = o.getName();
                // 自闭和标签是空字符串
                String value = o.getValue();
                map.put(name, value);
            }
            result.add(map);
        }
        System.out.println(result);
    }
}