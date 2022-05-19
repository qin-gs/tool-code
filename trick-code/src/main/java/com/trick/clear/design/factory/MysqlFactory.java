package com.trick.clear.design.factory;

public class MysqlFactory implements IFactory {
    @Override
    public IUser createIUser() {
        return new MysqlUser();
    }

    @Override
    public IDepartment createIDepartment() {
        return new MysqlDepartment();
    }
}
