package com.trick.clear.design.factory;

/**
 * 抽象工厂
 * 多个数据库 + 多张表
 */
public class Main {

    public static void main(String[] args) {

        Department department = new Department();

        MysqlFactory mysqlFactory = new MysqlFactory();
        IDepartment iDepartment = mysqlFactory.createIDepartment();
        iDepartment.insert(department);

        SqlServerFactory sqlServerFactory = new SqlServerFactory();
        IDepartment iDepartment1 = sqlServerFactory.createIDepartment();
        iDepartment1.insert(department);
    }
}
