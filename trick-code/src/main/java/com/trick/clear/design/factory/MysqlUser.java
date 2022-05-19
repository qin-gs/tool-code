package com.trick.clear.design.factory;

public class MysqlUser implements IUser {
    @Override
    public void insert(User user) {
        System.out.println("mysql insert user");
    }

    @Override
    public User getUser(int id) {
        System.out.println("mysql get user");
        return new User();
    }
}
