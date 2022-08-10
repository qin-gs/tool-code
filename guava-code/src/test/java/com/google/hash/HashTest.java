package com.google.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import com.google.pojo.User;
import org.hamcrest.object.HasEqualValues;
import org.junit.jupiter.api.Test;

public class HashTest {

    @Test
    public void bloom() {
        Funnel<User> funnel = new Funnel<>() {
            @Override
            public void funnel(User from, PrimitiveSink into) {
                into.putString(from.getName(), Charsets.UTF_8)
                        .putInt(from.getAge());
            }
        };
        BloomFilter<User> bloomFilter = BloomFilter.create(funnel, 100, 0.01);

        bloomFilter.put(User.Builder.anUser().name("aa").age(20).build());
        bloomFilter.put(User.Builder.anUser().name("bb").age(30).build());
        bloomFilter.put(User.Builder.anUser().name("cc").age(40).build());

        if (bloomFilter.mightContain(User.Builder.anUser().name("aa").age(20).build())) {
            System.out.println("aa is in bloom filter");
        }
    }

    @Test
    public void hash() {
        HashCode hello = Hashing.sha256().hashString("hello", Charsets.UTF_8);
        System.out.println(hello);

    }
}
