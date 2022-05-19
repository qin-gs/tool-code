package com.trick.clear.design.factory;

public class MysqlDepartment implements IDepartment {
    @Override
    public void insert(Department department) {
        System.out.println("mysql insert department");
    }

    @Override
    public Department getDepartment(int id) {
        System.out.println("mysql get department");
        return new Department();
    }
}
