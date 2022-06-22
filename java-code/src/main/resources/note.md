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



数组长度始终为 $2^n$ 

- 扩容条件：哈希冲突 + `size > threshold` 

  扩容后的元素在 原位置 或 原位置+oldCap；jdk1.8 尾插法

- 链表 -> 红黑树：数组长度>=64 + 链表长度>8



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



1. 判断键值对数组table[i]是否为空或为null，否则执行resize()进行扩容； 

2. 根据键值key计算hash值得到插入的数组索引i，如果table[i]==null，直接新建节点添加，转向⑥，如果table[i]不为空，转向③； 

3. 判断table[i]的首个元素是否和key一样，如果相同直接覆盖value，否则转向④，这里的相同指的是hashCode以及equals； 

4. 判断table[i] 是否为treeNode，即table[i] 是否是红黑树，如果是红黑树，则直接在树中插入键值对，否则转向⑤； 

5. 遍历table[i]，判断链表长度是否大于8，大于8的话把链表转换为红黑树，在红黑树中执行插入操作，否则进行链表的插入操作；遍历过程中若发现key已经存在直接覆盖value即可； 

6. 插入成功后，判断实际存在的键值对数量size是否超多了最大容量threshold，如果超过，进行扩容。



<img src="./img/HashMap.put.jpg" alt="HashMap#put" style="zoom:50%;" />



1. 内部的数据结构

   数组，链表，红黑树

   - 当链表超过 8 且数据总量超过 64 才会转红黑树。
   - 将链表转换成红黑树前会判断，如果当前数组的长度小于 64，那么会选择先进行数组扩容，而不是转换为红黑树，以减少搜索时间。

2. 是否允许空值

   允许一个 key 为 null

3. 有哪些参数

   size, threshold, initialCapacity, loadFactory, modCount, UNTREEIFY_THRESHOLD, TREEIFY_THRESHOLD

4. put 的流程

   如图所示

5. jdk1.8 的优化

   链表($O(n)$)  -> 红黑树($O(log(n))$)

   头插法 -> 尾插法

   扩容时判断索引位置

6. hash 计算

   ```text
   hash 扰动函数：混合原始哈希码的高位 和 低位，增加低位的随机性(同时保留的高位的一些信息)
   (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)
   ```

7. 数组长度 $2^n$

   h & (length - 1) 获取索引位置

8. 线程安全问题

   1.7 扩容(resize)造成环形链表，数据丢失

9. 扩容逻辑判断

   新位置 = 原位置 / 原位置+oldCap





#### HashSet / TreeSet

HashSet 内部有一个 HashMap，只使用 key

TreeSet 内部有一个 TreeMap，只使用 key



#### LinkedHashMap

加入了一个双向链表的头尾节点，将所有节点(包含 before 和 next 两个引用)串成一个双向循环链表，保留所有链表的插入顺序



#### HashTable

线程安全，使用 synchronized 对方法进行同步，k/v 都不许为 null

多线程访问时候，只要有一个线程访问或操作该对象，那其他线程只能阻塞等待需要的锁被释放，在竞争激烈的多线程场景中性能就会非常差



#### ConcurrentHashMap

[参考文章](https://zhuanlan.zhihu.com/p/355565143)

[参考文章](https://zhuanlan.zhihu.com/p/346803874)

k/v 都不能为 null，无法判断 没有这个 key 还是 value=null



jdk1.7：

数组，链表：Segment + HashEntry

分段锁，并发度 16 (程序运行时能够同时更新 ConcurrentHashMap且不产生锁竞争的最大线程数)

需要 2 次才能定位到当前 key 应该落在哪个槽

- put：
  - 先定位到相应的 Segment ，然后再进行 put 操作，
  - 尝试自旋获取锁，
  - 如果重试的次数达到了 `MAX_SCAN_RETRIES` 则改为阻塞锁获取，保证能获取成功。

- get：
  - 根据 key 计算出 hash 值定位到具体的 Segment 
  - 根据 hash 值获取定位 HashEntry 对象
  - 对 HashEntry 对象进行链表遍历，找到对应元素





jdk1.8

数组，链表，红黑树：CAS + synchronized

锁的粒度更小，只需要锁住链表头节点(红黑树根节点)

使用 synchronized 替换了 ReentrantLock

- put：

  - 判断 k/v 是否为 null，是的话抛出异常

  - 根据 key 计算出 hash 值，进入死循环 (确保插入成功)；

  - 判断 table 是否需要进行初始化；

  - 定位到 Node，拿到首节点 f，判断首节点 f：

    - 如果为 null ，则通过 CAS 的方式尝试添加；
    - 如果为 `f.hash = MOVED = -1` ，说明其他线程在扩容，参与一起扩容；
    - 如果都不满足 ，synchronized 锁住 f 节点，判断是链表还是红黑树，遍历插入 (比较 hash，插入或覆盖；尾插法)；

  - 判断是否需要进行 链表 和 红黑树的转换

  - addCount 将当前 size + 1 ；同时判断是否需要扩容

    并发扩容 TODO

    >  当在链表长度达到 8 的时候，数组扩容或者将链表转换为红黑树。

- get:

  - 根据 key 计算出 hash 值，判断数组是否为空；

  - 如果是首节点，就直接返回；

  - 如果是红黑树结构，就从红黑树里面查询；

  - 如果是链表结构，循环遍历判断。

    get 方法不需要加锁。因为 Node 的元素 value 和指针 next 是用 volatile 修饰的，在多线程环境下线程A修改节点的 value 或者新增节点的时候是对线程B可见的。



> `sizeCtl<-1` 表示有 `N-1` 个线程正在执行扩容操作，如 `-2` 就表示有 `2-1` 个线程正在扩容。
>
> `sizeCtl=-1` 占位符，表示当前正在初始化数组。
>
> `sizeCtl=0` 默认状态，表示数组还没有被初始化。
>
> `sizeCtl>0` 记录下一次需要扩容的大小。



- size

  使用一个数组 (`CounterCell[]`)来计数，线程需要计数的时候，都通过随机的方式获取一个数组下标的位置进行操作，这样就可以尽可能的降低了锁的粒度，最后获取 `size` 时，则通过遍历数组来实现计数；竞争激烈的时候会扩容

  





#### 红黑树

[参考文章](https://tech.meituan.com/2016/12/02/redblack-tree.html)





#### ReentrantLock

[参考文章](https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html)





