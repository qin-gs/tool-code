package com.trick.clear.design.factory;

public class SqlServerDepartment implements IDepartment {
    @Override
    public void insert(Department department) {
        System.out.println("sqlserver insert department");
    }

    @Override
    public Department getDepartment(int id) {
        System.out.println("sqlserver get department");
        return new Department();
    }
}
