package com.trick.clear.design.factory;

public class SqlServerUser implements IUser {
    @Override
    public void insert(User user) {
        System.out.println("sqlserver insert user");
    }

    @Override
    public User getUser(int id) {
        System.out.println("sqlserver insert user");
        return new User();
    }
}
