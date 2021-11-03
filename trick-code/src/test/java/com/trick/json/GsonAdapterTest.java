package com.trick.json;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 相比于JsonDeserializer和JsonSerializer，更推荐使用TypeAdapter但是比较麻烦()
 * {@code @JsonAdapter}注解用来标记类，不用再通过registerTypeAdapter注册了
 */
@DisplayName("gson TypeAdapter 测试")
public class GsonAdapterTest {

    @Test
    public void test() {
        new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
    }

    private static class UserTypeAdapter extends TypeAdapter<User> {

        /**
         * 序列化
         */
        @Override
        public void write(JsonWriter out, User value) throws IOException {
            out.beginObject(); // 添加一个 {
            // out.beginArray(); // 如果是数组对象使用这个 添加 [
            // 依次设置属性
            out.name("id").value(value.getId());
            out.name("habits").value(StringUtils.join(value.getHabits(), ","));
            for (User x : value.getUsers()) {
                out.beginObject();
                out.name("id").value(x.getId());
                out.name("name").value(x.getName());
                out.endObject();
            }
            // out.endArray(); // 补上 ]
            out.endObject(); // 补上 }
        }

        /**
         * 反序列化
         */
        @Override
        public User read(JsonReader in) throws IOException {
            User user = new User();
            in.beginObject();
            // 依次获取值进行设置
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "id": {
                        user.setId(in.nextString());
                        break;
                    }
                    case "habits": {
                        user.setHabits(Arrays.asList(in.nextString().split(",")));
                        break;
                    }
                    case "users": {
                        in.beginArray();
                        List<User> users = new ArrayList<>();
                        while (in.hasNext()) {
                            in.beginObject();
                            User u = new User();
                            while (in.hasNext()) {
                                switch (in.nextName()) {
                                    case "id": {
                                        u.setId(in.nextString());
                                        break;
                                    }
                                    case "height": {
                                        u.setHeight((float) in.nextDouble());
                                        break;
                                    }
                                    default: {
                                        break;
                                    }
                                }
                            }
                            in.endObject();
                        }
                        in.endArray();
                        user.setUsers(users);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            in.endObject();
            return user;
        }
    }
}
