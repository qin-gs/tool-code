package com.google.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class User {
    private String name;
    private int age;
    private String address;
    private String phone;
    private Date createTime;
    private Date updateTime;
    private List<String> hobbies;
    private Set<String> interests;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", hobbies=" + hobbies +
                ", interests=" + interests +
                '}';
    }


    public static final class Builder {
        private String name;
        private int age;
        private String address;
        private String phone;
        private Date createTime;
        private Date updateTime;
        private List<String> hobbies;
        private Set<String> interests;

        private Builder() {
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder hobbies(List<String> hobbies) {
            this.hobbies = hobbies;
            return this;
        }

        public Builder interests(Set<String> interests) {
            this.interests = interests;
            return this;
        }

        public User build() {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            user.setAddress(address);
            user.setPhone(phone);
            user.setCreateTime(createTime);
            user.setUpdateTime(updateTime);
            user.setHobbies(hobbies);
            user.setInterests(interests);
            return user;
        }
    }
}
