package com.trick.json;

import com.google.gson.*;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Serialization:序列化，使Java对象到Json字符串的过程。
 * Deserialization：反序列化，字符串转换成Java对象。
 * JSON数据中的JsonElement有下面这四种类型：
 * JsonPrimitive —— 例如一个字符串或整型
 * JsonObject—— 一个以 JsonElement 名字（类型为 String）作为索引的集合。也就是说可以把 JsonObject 看作值为 JsonElement 的键值对集合。
 * JsonArray—— JsonElement 的集合。注意数组的元素可以是四种类型中的任意一种，或者混合类型都支持。
 * JsonNull—— 值为null
 * <p>
 * https://www.jianshu.com/p/fc5c9cdf3aab
 */
@DisplayName("gson serialize 测试")
public class GsonSerializeTest {

    /**
     * 修改序列化后对象中的字段名
     */
    public void serializeTest() {
        // 1. @SerializeName 指定
        // 2. 使用JsonSerializer类
        new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer()) // 支持泛型(User<T> {T data})，不支持继承
                .registerTypeHierarchyAdapter(Number.class, new NumberSerializer()) // 支持承，不支持泛型
                .create();
    }

    private static class NumberSerializer implements JsonSerializer<Number> {

        @Override
        public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(String.valueOf(src));
        }
    }

    private static class UserSerializer implements JsonSerializer<User> {

        @Override
        public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jo = new JsonObject();
            // 手动设置序列化后的键
            jo.addProperty("user-id", src.getId());
            // 或者直接使用context
            jo.add("users", context.serialize(src.getUsers()));

            JsonArray ja = new JsonArray();
            src.getHabits().forEach(ja::add);
            return jo;
        }
    }

    private static class UserDeserializer implements JsonDeserializer<User> {

        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jo = json.getAsJsonObject();
            User user = new User();
            user.setId(jo.get("id").getAsString());

            JsonArray habitsArray = jo.get("habits").getAsJsonArray();
            List<String> habits = new ArrayList<>(habitsArray.size());
            habitsArray.forEach(x -> habits.add(x.getAsString()));

            return null;
        }
    }
}
