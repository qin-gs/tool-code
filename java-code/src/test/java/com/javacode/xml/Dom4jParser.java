package com.javacode.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * dom4j 解析 xml
 * 和JDOM相比，DOM4J的性能更为优异，功能更加强大，使用更加便捷，因此也更为常用
 */
@DisplayName("dom4j 解析 xml")
public class Dom4jParser {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void test() throws Exception {
        List<Map<String, String>> result = new ArrayList<>();
        // 创建Reader对象
        SAXReader reader = new SAXReader();
        // 加载xml
        String file = Objects.requireNonNull(DomParser.class.getResource("/demo.xml")).getFile();
        Document document = reader.read(file);
        // 获取根节点
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()) {
            Element stu = (Element) iterator.next();
            List<Attribute> attributes = stu.attributes();
            for (Attribute attribute : attributes) {
                String name = attribute.getName();
                String value = attribute.getValue();
                System.out.println(name + " -> " + value);
            }

            Map<String, String> map = new HashMap<>(4);
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()) {
                Element stuChild = (Element) iterator1.next();
                String name = stuChild.getName();
                String value = stuChild.getStringValue();
                map.put(name, value);
            }
            result.add(map);
        }

        System.out.println(result);
    }
}