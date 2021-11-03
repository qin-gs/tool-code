package com.apache.beanutils;

import com.apache.pojo.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class Main {

    @Test
    public void test1() throws Exception {
        User user = new User();
        user.setList(Arrays.asList("1", "2", "3"));
        // class必须是public
        // 会输出大量日志信息
        BeanUtils.setProperty(user, "name", "qqq");
        System.out.println(user);

        // 获取属性值
        String name = BeanUtils.getProperty(user, "name");
        System.out.println(name);

        // 以数组的形式返回属性值
        String[] lists = BeanUtils.getArrayProperty(user, "list");
        System.out.println(Arrays.toString(lists));

        // 返回简单属性值
        String n = BeanUtils.getSimpleProperty(user, "name");
        // 以字符串的形式返回属性值，如果是列表数组，返回第一个
        String list = BeanUtils.getSimpleProperty(user, "list");
        System.out.println(n);
        System.out.println(list);

        // 返回列表或数组指定位置的元素
        String list1 = BeanUtils.getIndexedProperty(user, "list[1]");
        System.out.println(list1);
        String list2 = BeanUtils.getIndexedProperty(user, "list", 2);
        System.out.println(list2);
    }

    @Test
    public void test2() throws Exception {
        Map<String, Object> map = Map.of("name", "qqq", "age", 12, "address", "cn");
        User user = new User();
        // 将对象中的属性放到map中
        BeanUtils.populate(user, map);
        System.out.println(user);
    }

    @Test
    public void test3() throws Exception {
        User user1 = new User();
        user1.setName("qqq");
        user1.setAge(12);
        user1.setAddress("cn");
        user1.setList(Arrays.asList("1", "2", "3"));

        User user2 = new User();

        // 浅拷贝
        BeanUtils.copyProperties(user2, user1);
        System.err.println(user2);

        Object o = BeanUtils.cloneBean(user1); // 浅拷贝
        System.out.println(o);
    }

    @Test
    public void test4() throws Exception {
        User user = new User();
        user.setName("qqq");
        user.setAge(12);
        user.setAddress("cn");
        user.setList(Arrays.asList("1", "2", "3"));
        // 将bean中的属性放到map中
        Map<String, String> describe = BeanUtils.describe(user);
        System.out.println(describe);
    }

    @Test
    public void test5() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        User user = new User();
        user.setName("qqq");
        user.setAge(12);
        user.setAddress("cn");
        user.setList(Arrays.asList("1", "2", "3"));
        user.setMap(Map.of("k1", "v1", "k2", 2));

        // 从集合中拿元素
        String property = BeanUtils.getMappedProperty(user, "map", "k1");
        System.out.println(property);
    }

    @Test
    public void test6() {
        System.out.println(ConvertUtils.convert(123L));
        Object l = ConvertUtils.convert("123", Long.class);
        System.out.println(l.getClass());

        Object s = ConvertUtils.convert(Boolean.TRUE, String.class);
        System.out.println(s);
    }
}

