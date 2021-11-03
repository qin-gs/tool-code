package com.apache.collection4;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.list.PredicatedList;
import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.collections4.list.TransformedList;
import org.apache.commons.collections4.map.*;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("all")
@DisplayName("apache common collections测试类")
public class CollectionTest {

    @Test
    public void collectionTest() {
        List<String> list1 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> list2 = Stream.of("c", "d", "e", "f").collect(Collectors.toList());


        CollectionUtils.isEmpty(list1);
        CollectionUtils.addAll(list1, list2);
        CollectionUtils.containsAll(list1, list2);
        CollectionUtils.containsAny(list1, list2);
        // 差集
        CollectionUtils.subtract(list1, list2);
        // 交集 https://segmentfault.com/q/1010000021249187
        CollectionUtils.intersection(list1, list2);

        // 获取在另一个集合中存在的元素
        CollectionUtils.retainAll(list1, list2);

        // 合并并去重
        CollectionUtils.union(list1, list2);
        // 全排列
        System.out.println(CollectionUtils.permutations(list1));

    }

    @Test
    public void listTest() {
        ListUtils utils;
        SetUtils setUtils;

        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        // 固定大小，不能添加，但可以修改值
        ListUtils.fixedSizeList(list);
        // 保证list中不存在重复元素，添加元素是如果存在不处理
        SetUniqueList<String> setUniqueList = SetUniqueList.setUniqueList(list);
        // 对添加进去的元素进行转换，原来的也会被转换
        TransformedList<String> transformedList = TransformedList.transformedList(list, x -> x.concat("_"));
        // 原来的不会被转换
        TransformedList.transformingList(list, String::trim);

        // 匹配的元素会被加入集合，否则抛异常
        PredicatedList<String> predicatedList = PredicatedList.predicatedList(list, x -> x.length() > 2);

        // 按照添加顺序排列的集合
        ListOrderedSet<String> listOrderedSet = new ListOrderedSet<>();
        // 带计数功能
        HashBag<String> hashBag = new HashBag<>();
        hashBag.add("");
        hashBag.getCount("a"); // 统计元素次数
    }

    /**
     * map扩展
     */
    @Test
    public void mapTest() {
        // MultiValuedMap 一个key运行多个value
        ListValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key1", "val1");
        map.put("key1", "val2");
        map.put("key2", "val2");
        System.out.println(map.containsKey("key1"));
        List<String> key1 = map.get("key1"); // 返回List
        System.out.println(map.values()); // 返回所有的value
        Map<String, Collection<String>> asMap = map.asMap(); // 转换成原生的map

        // 大小写不敏感的map，会将key统一转成小写
        CaseInsensitiveMap<String, String> insensitiveMap = new CaseInsensitiveMap<>();
        insensitiveMap.put("key1", "val1");
        insensitiveMap.put("key2", "val2");
        System.out.println(insensitiveMap.get("kEy1"));

        // 按插入顺序排列的的map
        ListOrderedMap<String, String> orderedMap = new ListOrderedMap<>();
        orderedMap.put("key1", "val1");
        orderedMap.put("key3", "val3");
        orderedMap.put("key2", "val2");
        System.out.println(orderedMap.keyList());

        // lruMap 最近最少使用，默认容量100
        LRUMap<String, String> lruMap = new LRUMap<>();

        // 达到过期时间，下次访问时删除
        long live = 2000L;
        PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<String, String> policy = new PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<>(live);
        PassiveExpiringMap<String, String> expiringMap = new PassiveExpiringMap<>(policy);

        // 用软引用做缓存
        ReferenceMap<String, String> referenceMap = new ReferenceMap<>(AbstractReferenceMap.ReferenceStrength.SOFT, AbstractReferenceMap.ReferenceStrength.SOFT);

        // 键值双向查找, key,value都不允许重复
        BidiMap<String, String> bidiMap = new TreeBidiMap<>();
        bidiMap.put("dog", "狗");
        bidiMap.get("dog");
        bidiMap.getKey("狗");
        bidiMap.inverseBidiMap(); // key value互换，修改它也会影响原来的map
    }

    @Test
    public void test() {
        final List<Integer> list = Stream.of(1, 1, 2, 3, 6, 6).collect(Collectors.toList());
        final List<Integer> list2 = Stream.of(1, 2, 2, 4, 6, 6).collect(Collectors.toList());
        System.out.println(CollectionUtils.retainAll(list, list2)); // [1, 1, 2, 6, 6]
        System.out.println(CollectionUtils.retainAll(list2, list)); // [1, 2, 2, 6, 6]
        System.out.println(CollectionUtils.intersection(list, list2)); // [1, 2, 6, 6]
        System.out.println(CollectionUtils.intersection(list2, list)); // [1, 2, 6, 6]
        System.out.println(CollectionUtils.union(list, list2)); // [1, 1, 2, 2, 3, 4, 6, 6]
        System.out.println(CollectionUtils.subtract(list, list2)); // [1, 3]
        System.out.println(CollectionUtils.removeAll(list, list2)); // [3]
    }
}
