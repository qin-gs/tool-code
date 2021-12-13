package com.javacode.xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Objects;

/**
 * 事件驱动的解析机制
 * SAX解析器会逐行阅读，当读取到一个标记的开始、属性、内容、结束时触发事件
 * 需要编写事件触发的相应处理程序
 * <pre>
 *   优点
 *     无需等待数据全部加载完毕，立即开始分析
 *     逐行加载，节省内存
 *     不必解析整个文档，可以在满足某个条件时停止解析
 *   缺点
 *     单向解析，无法对之前的数据再进行操作
 *     逐行解析无法得知复杂的元素的层次，只能维护父子关系的节点
 *     只读解析，无法修改内容
 * </pre>
 */
@DisplayName("sax 解析 xml")
public class SaxParser {

    @Test
    public void test() throws Exception {
        String file = Objects.requireNonNull(DomParser.class.getResource("/demo.xml")).getFile();
        // 获取 SAXParserFactory 实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 获取 SAXParser 实例
        SAXParser saxParser = factory.newSAXParser();
        // 创建 Handel 对象
        SAXDemoHandel handel = new SAXDemoHandel();
        saxParser.parse(file, handel);
    }
}

class SAXDemoHandel extends DefaultHandler {

    /**
     * 遍历xml文件开始标签
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("sax解析开始");
    }

    /**
     * 遍历xml文件结束标签
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("sax解析结束");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("student")) {
            System.out.println("============开始遍历student=============");
        } else if (!qName.equals("class")) {
            System.out.print("节点名称: " + qName + "----");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("student")) {
            System.out.println(qName + "遍历结束");
            System.out.println("============结束遍历student=============");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String value = new String(ch, start, length).trim();
        if (!value.equals("")) {
            System.out.println(value);
        }
    }
}
