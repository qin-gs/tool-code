package com.trick.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.*;

@DisplayName("jackson 测试")
public class JacksonTest {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void jsonToJava() throws IOException {

        {
            // 字符串 / 输入流 / 文件 / url / 字节流 / 二进制数组  -->  java对象
            String s = "{\"id\":  \"1\", \"name\":  \"qqq\"}";

            StringReader reader = new StringReader(s);
            File file = new File("user.json");
            URL url = new URL("file:data/user.json");
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

            User user = MAPPER.readValue(s, User.class);
        }
        {
            // json数组字符串  -->  java 对象数组 / list
            String s = "[{\"id\":  \"1\", \"name\":  \"qqq\"}, {\"id\":  \"2\", \"name\":  \"www\"}]";
            User[] users = MAPPER.readValue(s, User[].class);
            // 泛型
            List<User> list = MAPPER.readValue(s, new TypeReference<List<User>>() {
            });
            System.out.println("list = " + list);
        }
        {
            // json 字符串  -->  map
            String s = "{\"id\":  \"1\", \"name\":  \"qqq\"}";
            Map<String, Object> map = MAPPER.readValue(s, new TypeReference<Map<String, Object>>() {
            });
            System.out.println("map = " + map);
        }

        {
            ObjectMapper mapper = new ObjectMapper();
            // 忽略字符串中的一些字段 (java对象中没有)
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 规定基本数据类型不能为 null
            mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        }

        {
            String s = "{\"id\":  \"1\", \"name\":  \"qqq\"}";
            // 自定义反序列化
            SimpleModule module = new SimpleModule("UserDeserializer", new Version(3, 1, 8, null, null, null));
            module.addDeserializer(User.class, new UserDeserializer(User.class));
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(module);

            User user = mapper.readValue(s, User.class);
            System.out.println("user = " + user);

        }

    }

    @Test
    public void javaToJson() throws IOException {
        {
            // java 对象  -->  输出流(会先转成json) / 字符串 / 字节数组
            User user = new User();
            user.setId("123");
            user.setName("www");
            user.setAge(23);

            MAPPER.writeValue(new FileOutputStream("data/user.json"), user);

            String s = MAPPER.writeValueAsString(user);
            byte[] bytes = MAPPER.writeValueAsBytes(user);
        }

        {
            // 自定义序列化
            User user = new User();
            user.setId("123");
            user.setName("www");
            user.setAge(23);
            SimpleModule module = new SimpleModule("UserSerializer", new Version(2, 1, 3, null, null, null));
            module.addSerializer(User.class, new UserSerializer(User.class));

            MAPPER.registerModule(module);
            String s = MAPPER.writeValueAsString(user);
            System.out.println("s = " + s);
        }
    }

    @Test
    public void dateToJson() throws JsonProcessingException {
        // 默认 Date -> long
        User user = new User();
        user.setId("123");
        user.setName("www");
        user.setRegisterDate(new Date());

        String s = MAPPER.writeValueAsString(user);
        System.out.println("s = " + s);

        // 修改成 字符串
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String s1 = MAPPER.writeValueAsString(user);
        System.out.println("s1 = " + s1);
    }

    @Test
    public void jsonNode() throws JsonProcessingException {
        String s = "{\"id\":  \"1\", \"name\":  \"qqq\", \"age\": \"23\"}";
        {
            JsonNode jsonNode = MAPPER.readValue(s, JsonNode.class);
            // 或
            JsonNode readTree = MAPPER.readTree(s);

            // get 之后还是一个 JsonNode，可以嵌套获取
            JsonNode idNode = jsonNode.get("id");
            String id = idNode.asText();
            System.out.println("id = " + id);

            JsonNode nameNode = jsonNode.get("name");
            String name = nameNode.asText();
            System.out.println("name = " + name);

            JsonNode ageNode = jsonNode.get("age");
            int age = ageNode.asInt();
            System.out.println("age = " + age);

            // JsonNode  -->  java 对象
            JsonNode node = MAPPER.readTree(s);
            User user = MAPPER.treeToValue(node, User.class);
            System.out.println("user = " + user);
        }

        {
            String user = "{\"id\":  \"1\", \"name\":  \"q\", \"age\": \"23\", \"login\":  {\"username\":  \"qqq\", \"password\":  \"word\"}}";
            // 从字符串中获取 jsonNode，获取字段值(可以提供默认值)
            JsonNode jsonNode = MAPPER.readTree(user);
            JsonNode id = jsonNode.get("id");
            JsonNode name = jsonNode.get("name");
            JsonNode age = jsonNode.get("age");
            System.out.println("age.asInt() = " + age.asInt());

            // 在路径中获取 jsonNode
            JsonNode username = jsonNode.at("/login/username");
            System.out.println("username.asText() = " + username.asText());
        }

        {
            ObjectNode objectNode = MAPPER.createObjectNode();
            objectNode.put("id", "q");
            objectNode.put("name", "w");
            objectNode.set("another", MAPPER.readTree(s));
            String string = objectNode.toPrettyString();
            System.out.println("string = " + string);
        }
    }

    @Test
    public void jsonParser() throws IOException {
        String s = "{\"id\":  \"1\", \"name\":  \"qqq\", \"age\": \"23\"}";

        {
            User user = new User();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(s);

            while (!parser.isClosed()) {
                JsonToken jsonToken = parser.nextToken();

                // 最后一个 jsonToken 是 null (会出现空指针异常)
                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = parser.getCurrentName();
                    if ("id".equals(fieldName)) {
                        user.setId(parser.getValueAsString());
                    } else if ("name".equals(fieldName)) {
                        user.setName(parser.getValueAsString());
                    } else if ("age".equals(fieldName)) {
                        user.setAge(parser.getValueAsInt());
                    }
                }
            }
            System.out.println("user = " + user);
        }
    }

    /**
     * JsonGenerator: 从 java 对象生成 json
     */
    @Test
    public void jsonGenerator() throws IOException {
        {
            JsonFactory factory = new JsonFactory();
            StringWriter writer = new StringWriter();
            JsonGenerator generator = factory.createGenerator(writer);

            generator.writeStartObject();
            generator.writeStringField("id", "q");
            generator.writeNumberField("age", 12);
            generator.writeEndObject();
            generator.close();

            String s = writer.getBuffer().toString();
            System.out.println("s = " + s);

        }
    }

    @Test
    public void anno() throws IOException {
        {

            // 注解
            // @JsonIgnore：注解字段 (忽略该字段)
            // @JsonIgnoreProperties：注解类 (忽略该类中的指定字段)
            // @JsonIgnoreType：注解类 (忽略该类)
            // @JsonAutoDetect：修饰符控制

            // @JsonSetter：处理 json 和 java对象名称不一致的问题
            // @JsonAnySetter：处理无法识别的字段
            // @JsonCreator：通过构造函数进行字段对应 (用在没有 set 函数的类)
            // @JacksonInject：注入属性
            InjectableValues.Std std = new InjectableValues.Std().addValue(String.class, "source");
            User user = MAPPER.reader(std)
                    .forType(User.class)
                    .readValue(new File("user.json"));

            // @JsonDeserialize：指定反序列化器 (0/1 -> false/true)

            // @JsonInclude：可以忽略一些属性 (非空)
            // @JsonSetter：通过 get 方法而不是直接通过字段获取字段值

        }
    }
}

/**
 * 自定义序列化
 */
class UserSerializer extends StdSerializer<User> {

    @Serial
    private static final long serialVersionUID = 2543279679624522609L;

    protected UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("age", value.getAge());
        gen.writeEndObject();
    }
}

/**
 * 自定义反序列化
 */
class UserDeserializer extends StdDeserializer<User> {
    @Serial
    private static final long serialVersionUID = 7803621750645345283L;

    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        User user = new User();

        while (!p.isClosed()) {

            JsonToken token = p.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldName = p.getCurrentName();
                // 上一个是 FIELD_NAME，下一个是 VALUE_STRING
                // token = p.nextToken();

                if ("id".equals(fieldName)) {
                    user.setId(p.getValueAsString());
                } else if ("name".equals(fieldName)) {
                    user.setName(p.getValueAsString() + " ---");
                }
            }
        }
        return user;
    }
}

/**
 * 忽略类中属性
 */
@Data
@JsonIgnoreProperties({"birth"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class User {

    private String id;
    private String name;
    private int age;
    @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
    private boolean married;
    /**
     * 序列化时忽略该属性
     */
    @JsonIgnore
    private float height;
    private Date registerDate;
    private String birth;
    private String address;
    private String phone;
    private DayOfWeek dayOfWeek;
    private List<String> habits;
    private List<User> users;
    private Map<String, Object> unknown;

    @JacksonInject
    private String source = null;

    public User() {
    }

    /**
     * 对于没有 set 方法的类，通过构造函数进行映射
     */
    @JsonCreator
    public User(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * json 中无法识别的字段调用该方法
     */
    @JsonAnySetter
    public void set(String fieldName, Object value) {
        unknown.put(fieldName, value);
    }

}

/**
 * 将 0/1 转成 false/true
 */
class OptimizedBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return !p.getText().equals("0");
    }
}