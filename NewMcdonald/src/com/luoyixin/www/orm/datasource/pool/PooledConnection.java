package com.luoyixin.www.orm.datasource.pool;

import java.sql.Connection;


/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource
 * @ClassName: PooledConnection
 * @create 2021/4/23-21:34
 * @Version: 1.0
 */
public class PooledConnection {
    /**
     * 存放真正的连接
     */
    public Connection connection;

    /**
     * 已经连接的时间，每个Connection不一样
     */
    private Long checkOutTime;

    public PooledConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Long getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Long checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PooledConnection that = (PooledConnection) o;
        return connection.equals(that.connection);
    }

    @Override
    public int hashCode() {
        return connection.hashCode();
    }
}
