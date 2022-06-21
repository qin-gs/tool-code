### 笔记



#### HashMap

线程不安全  [参考文章](https://tech.meituan.com/2016/06/24/java-hashmap.html)



哈希冲突

- 开放地址法 (线性探测)：指定位置有元素之后向后检测
- 再哈希法
- **链地址法**：将哈希表的节点作为一个链表的头部 或 书的根节点
- 公共溢出区域



一些变量：

- size：实际容量 ($ 2 < size <  2^{30} $) 
- threshold：阈值 (`initialCapacity * loadFactory`) 
- initialCapacity=16：默认容量
- loadFactory=0.75：负载因子
- modCount：`ConcurrentModificationException`
- UNTREEIFY_THRESHOLD=6：<=该值  红黑树($log(n)$) -> 链表($n/2$)
- TREEIFY_THRESHOLD=8：>=该值  链表 -> 红黑树



**数组 + 链表 + 红黑树**

- put：

  - null

    使用 table[0] 存放键为 null 的键值对；

    - 已存在：覆盖，返回旧的 value

    - 不存在：添加到链表头部 O(1)

  - 不为 null

    - hash(key)  ->  h & (length - 1)  ->  存储下标i

      如果 table[i] 有元素，遍历链表，比较是否存在相同的 key，存在则覆盖，否则保存在链表头部

      ```java
      // hash 扰动函数：混合原始哈希码的高位 和 低位，增加低位的随机性(同时保留的高位的一些信息)
      (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)
      ```



数组长度始终为 $2^n$ 

- 扩容条件：哈希冲突 + `size > threshold` 

  扩容后的元素在 原位置 或 原位置+oldCap；jdk1.8 尾插法

- 链表 -> 红黑树：数组长度>=64 + 链表长度>8



1. 首先根据 key 的值计算 hash 值，找到该元素在数组中存储的下标；
2. 如果数组是空的，则调用 resize 进行初始化；
3. 如果没有哈希冲突直接放在对应的数组下标里；
4. 如果冲突了，且 key 已经存在，就覆盖掉 value；
5. 如果冲突后，发现该节点是红黑树，就将这个节点挂在树上；
6. 如果冲突后是链表，判断该链表是否大于 8 ，如果大于 8 并且数组容量小于 64，就进行扩容；如果链表节点大于 8 并且数组的容量大于 64，则将这个结构转换为红黑树；否则，链表插入键值对，若 key 存在，就覆盖掉 value。

<img src="./img/HashMap.put.jpg" alt="HashMap#put" style="zoom:50%;" />









#### HashSet / TreeSet

HashSet 内部有一个 HashMap，只使用 key

TreeSet 内部有一个 TreeMap，只使用 key



#### LinkedHashMap

加入了一个双向链表的头尾节点，将所有节点(包含 before 和 next 两个引用)串成一个双向循环链表，保留所有链表的插入顺序



#### HashTable

线程安全，使用 synchronized 对方法进行同步，k/v 都不许为 null



#### ConcurrentHashMap

















