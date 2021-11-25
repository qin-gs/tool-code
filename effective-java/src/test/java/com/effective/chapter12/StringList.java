package com.effective.chapter12;

import java.io.*;

/**
 * 序列化测试类
 */
public class StringList implements Serializable {
    @Serial
    private static final long serialVersionUID = 5220078187501217758L;

    private transient int size = 0;
    private transient Entry head = null;

    private static class Entry {
        String data;
        Entry next;
        Entry previous;
    }

    public final void add(String s) {
        // ...
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        // 调用该方法保证在未来版本中如果某个实例被序列化，还能保持向前或向后兼容
        s.defaultWriteObject();
        s.writeInt(size);

        for (Entry e = head; e != null; e = e.next) {
            s.writeObject(e.data); // 只需要序列化数据，Entry 这个类不需要
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject(); // 保证兼容性
        int numElements = s.readInt();
        for (int i = 0; i < numElements; i++) {
            add(((String) s.readObject()));
        }
    }
}
