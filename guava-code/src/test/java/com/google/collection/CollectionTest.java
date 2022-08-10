package com.google.collection;

import com.google.common.collect.*;
import com.google.pojo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("不可变集合")
public class CollectionTest {

    @Test
    public void test() {
        // 不能存储 null 值
        ImmutableSet<String> set = ImmutableSet.of("red", "green", "blue");
        ImmutableSet<String> build = ImmutableSet.<String>builder().add("red", "green", "blue").build();

        // 有序的集合
        ImmutableSortedSet<String> sortedSet = ImmutableSortedSet.of("a", "b", "d", "c");

        ImmutableSet<String> copy = ImmutableSet.copyOf(set);

        ImmutableList<String> list = set.asList();

    }


    @Test
    public void multiSet() {
        // MultiSet
        // 没有元素顺序限制的 ArrayList<E>
        // Map<E, Integer>，键为元素，值为计数
        HashMultiset<String> multiset = HashMultiset.create();
        multiset.add("a", 1);
        multiset.add("b", 2);
        multiset.add("c", 3);

        System.out.println("multiset.count(\"b\") = " + multiset.count("b"));
        System.out.println("multiset.elementSet() = " + multiset.elementSet());
        System.out.println("multiset.entrySet() = " + multiset.entrySet());
        System.out.println("multiset.size() = " + multiset.size());
        System.out.println("multiset = " + multiset);
    }

    @Test
    public void multiMap() {
        // MultiMap
        // Map<K, List<V>>
        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        // Map<K, Set<V>>
        SetMultimap<String, String> setMultimap = HashMultimap.create();

        listMultimap.put("a", "1");
        listMultimap.put("a", "2");
        listMultimap.put("b", "3");
        listMultimap.put("b", "4");
        listMultimap.put("b", "5");
        listMultimap.put("c", "6");
        listMultimap.remove("c", "6");

        // [a=1, a=2, b=3, b=4, b=5]
        System.out.println(listMultimap.entries());
        System.out.println(listMultimap.size());
        System.out.println(listMultimap.containsKey("c"));
        // [] 返回空集合
        System.out.println(listMultimap.get("d"));
        // null 返回 null
        System.out.println(listMultimap.asMap().get("d"));
    }

    @Test
    public void biMap() {
        // 双向 map
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("a", "1");
        biMap.put("b", "2");
        biMap.put("2", "b");
        System.out.println(biMap);
        System.out.println(biMap.inverse());
    }


    @Test
    public void table() {
        // 多个 key 作为索引
        // Map<K1, Map<K2, V>>
        HashBasedTable<String, Integer, User> table = HashBasedTable.create();
        table.put("a", 1, User.Builder.anUser().name("a").age(1).build());
        table.put("a", 2, User.Builder.anUser().name("a").age(2).build());
        table.put("b", 2, User.Builder.anUser().name("b").age(2).build());
        table.put("c", 3, User.Builder.anUser().name("c").age(3).build());
        System.out.println(table.get("a", 1));
        System.out.println(table.row("a")); // Map<C, V>
        System.out.println(table.rowMap()); // Map<R, Map<C, V>>
        System.out.println(table.cellSet()); // Set<Cell<R, C, V>>
    }

    @Test
    public void instance() {
        // 泛型是支持的上界
        MutableClassToInstanceMap<Number> map = MutableClassToInstanceMap.create();
        map.put(Integer.class, 1);
        map.put(Long.class, 2L);
        System.out.println(map);
    }

    @Test
    public void range() {
        // 区间合并
        TreeRangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        rangeSet.add(Range.closed(11, 15));
        rangeSet.add(Range.openClosed(15, 20));

        boolean contains = rangeSet.contains(16);
        System.out.println(contains);

        System.out.println(rangeSet);
    }


}
