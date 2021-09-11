package com.trick.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@DisplayName("Gson 测试")
public class GsonTest {

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

    /**
     * 序列化空值null
     */
    @Test
    public void test() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String s = gson.toJson(user);
        System.out.println(s);
    }

    /**
     * 忽略策略，不管是序列化还是反序列化都会起作用
     * 忽略一些类型的字段
     * 忽略一些名称的字段
     * 忽略一些指定修饰符修饰的字段
     * <p>
     * 还可以只选择一个起作用
     */
    @Test
    public void excludeTest() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.FINAL)
                .setExclusionStrategies(new ExclusionStrategy() {
                    /**
                     * 忽略字段名称中包含-的字段
                     */
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().contains("_");
                    }

                    /**
                     * 忽略Date类型的字段
                     */
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return Objects.equals(clazz, Date.class);
                    }
                })
                .create();
        String s = gson.toJson(user);
        System.out.println(s);

        // new GsonBuilder()
        //         .addDeserializationExclusionStrategy()
        //         .addSerializationExclusionStrategy()
        //         .create();
    }

    /**
     * 序列化一些特殊值 NaN，Infinity，-Infinity
     */
    @Test
    public void floatTest() {
        user.setHeight(Float.NEGATIVE_INFINITY);
        Gson gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .create();
        String s = gson.toJson(user);
        System.out.println(s);
    }

    /**
     * 使用@Expose或transient忽略一些字段
     * 需要excludeFieldsWithoutExposeAnnotation方法使之生效
     */
    @Test
    public void exposeTest() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String s = gson.toJson(user);
        System.out.println(s);
        // User user = gson.fromJson(s, User.class);
        // System.out.println(user);
    }

    /**
     * 字段上声明 @Since @Until 表明哪些版本生效
     */
    @Test
    public void sinceUntilTest() {
        new GsonBuilder()
                .setVersion(1.0)
                .create();
    }

    /**
     * 映射泛型
     */
    @Test
    public void genericTest() {
        List<User> users = Arrays.asList(user, new User());
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        String s = gson.toJson(users);
        List<User> o = gson.fromJson(s, new TypeToken<List<User>>() {
        }.getType());
        System.out.println(o);
    }

    @Test
    public void adapterTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<User>>() {
                        }.getType(),
                        new JsonSerializer<List<User>>() {
                            @Override
                            public JsonElement serialize(List<User> src, Type typeOfSrc, JsonSerializationContext context) {
                                JsonArray user = new JsonArray();
                                src.forEach(x -> user.add(x.getId()));
                                return user;
                            }
                        })
                .create();

        // 反序列化 {"year": "2021", "month": "09", "day": "11"} -> User{date: "2021-09-11"}
        new GsonBuilder()
                .registerTypeAdapter(User.class,
                        new JsonDeserializer<User>() {
                            @Override
                            public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                JsonObject jo = json.getAsJsonObject();
                                // 手动组装成Date
                                Date date = new Date(
                                        jo.get("year").getAsInt(),
                                        jo.get("month").getAsInt(),
                                        jo.get("day").getAsInt()
                                );
                                User user = new User();
                                // 手动获取并赋值属性
                                if (jo.has("name")) {
                                    user.setName(jo.get("name").getAsString());
                                } else {
                                    user.setName("defaultName");
                                }
                                user.setRegisterDate(date);
                                return user;
                            }
                        })
                .create();
    }


    private static class ContextInstanceCreator implements InstanceCreator<User> {
        // 例如ApplicationContext
        private Object context;

        public ContextInstanceCreator(Object context) {
            this.context = context;
        }

        @Override
        public User createInstance(Type type) {
            User user = new User();
            // 手动设置进去
            // user.setContext(context);
            return user;
        }
    }

    @Test
    public void instanceTest() {
        // TODO 其他属性需不需要手动设置
        new GsonBuilder()
                .registerTypeAdapter(User.class,
                        new ContextInstanceCreator(new Object()))
                .create();
    }

}
