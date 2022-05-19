package com.trick.clear.design.factory;

public class SqlServerFactory implements IFactory {
    @Override
    public IUser createIUser() {
        return new SqlServerUser();
    }

    @Override
    public IDepartment createIDepartment() {
        return new SqlServerDepartment();
    }
}
