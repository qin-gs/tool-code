package com.trick.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Date;

@DisplayName("GsonBuilder 测试")
public class GsonBuilderTest {

    public static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setId("user");
        user.setName("qqq");
        user.setAge(0);
        user.setHeight(180.0F);
        user.setRegisterDate(new Date());
        user.setBirth("2021-09-06");
        user.setAddress("cn");
        user.setPhone("119");
        user.setDayOfWeek(DayOfWeek.MONDAY);
    }

    @Test
    public void test() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setVersion(1.0F)
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE) // 字段命名策略，两个都可以
                // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE) // 字段命名策略
                .disableInnerClassSerialization() // 禁止序列化内部类
                .generateNonExecutableJson() // 生成不可执行的json，开头多了 )]}' 四个字符
                .disableHtmlEscaping() // 禁止转义html标签
                .setPrettyPrinting() // 格式输出
                .create();

        gson.toJson(user, System.out);
    }
}
