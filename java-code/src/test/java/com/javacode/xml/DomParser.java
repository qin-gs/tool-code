package com.javacode.xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 * DOM 解析
 * DOM将XML文档解析成树状结构，可以通过操作此文档树来获取、修改、删除数据。
 * <pre>
 *   优点
 *     文档被加载至内存，允许对数据和结构进行更改
 *     支持双向解析数据
 *   缺点
 *     文档全部被加载至内存中，消耗资源大
 * </pre>
 */
@DisplayName("dom 解析 xml")
public class DomParser {

    @Test
    public void test() throws Exception {
        String file = Objects.requireNonNull(DomParser.class.getResource("/demo.xml")).getFile();
        Document document = getXml(file);

        // 获取到所有的 student 节点
        NodeList list = document.getElementsByTagName("student");
        // List<Map<String, String>> students = element(list);

        List<Map<String, String>> students = node(list);
        System.out.println(students);
    }

    public Document getXml(String path) throws Exception {
        File f = new File(path);
        // DocumentBuilderFactory.newInstance() 得到创建 DOM 解析器的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 得到 DOM 解析器对象
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 用文档解析器解析一个文件放到document对象里
        return builder.parse(f);
    }

    /**
     * 用 Element 方式
     */
    public List<Map<String, String>> element(NodeList list) {

        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Map<String, String> map = new HashMap<>(4);
            Element element = (Element) list.item(i);
            NodeList childNodes = element.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    // 获取节点名
                    String nodeName = childNodes.item(j).getNodeName();
                    // 获取节点值，自闭和节点需要额外判断
                    Node node = childNodes.item(j).getFirstChild();
                    String nodeValue = node == null ? "" : node.getNodeValue();
                    map.put(nodeName, nodeValue);
                }
            }
            result.add(map);
        }
        return result;
    }

    /**
     * 用 Node 方式
     */
    public List<Map<String, String>> node(NodeList list) {
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Map<String, String> map = new HashMap<>(4);
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    // 获取节点名
                    String nodeName = childNodes.item(j).getNodeName();
                    // 获取节点值，自闭和节点需要额外判断
                    Node n = childNodes.item(j).getFirstChild();
                    String nodeValue = n == null ? "" : n.getNodeValue();
                    map.put(nodeName, nodeValue);
                }
            }
            result.add(map);
        }
        return result;
    }

}