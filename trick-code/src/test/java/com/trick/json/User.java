package com.trick.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

public class User {

    private String id;
    // value 修改序列化时的名字
    // alternate 用在反序列化的时候，反序列化时如果同时存在fullName和secondName，会将最后的那个值赋值给字段
    @SerializedName(value = "fullName", alternate = "secondName")
    private String name;
    private int age;
    private float height;
    private Date registerDate;
    private String birth;
    private String address;
    // 不进行序列化，但是需要反序列化
    @Expose(serialize = false, deserialize = true)
    private String phone;
    private DayOfWeek dayOfWeek;
    private List<String> habits;
    private List<User> users;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", registerDate=" + registerDate +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", habits=" + habits +
                ", users=" + users +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<String> getHabits() {
        return habits;
    }

    public void setHabits(List<String> habits) {
        this.habits = habits;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

