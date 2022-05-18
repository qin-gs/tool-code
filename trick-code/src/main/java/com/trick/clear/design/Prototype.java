package com.trick.clear.design;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 原型：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象
 */
public class Prototype {

    public static void main(String[] args) {
        RealizeType type = new RealizeType("qqq");
        RealizeType clone = type.clone();

        type.setName("www");

        System.out.println("type = " + type);
        System.out.println("clone = " + clone);
    }
}

@Setter
@Getter
@ToString
@NoArgsConstructor
class RealizeType implements Cloneable {

    private String name;

    public RealizeType(String name) {
        this.name = name;
    }

    @Override
    public RealizeType clone() {
        try {
            // jackson 需要有 get 方法，无参构造函数
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this);
            return mapper.readValue(json, this.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}