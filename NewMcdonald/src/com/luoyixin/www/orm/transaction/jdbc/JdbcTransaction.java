package com.luoyixin.www.orm.transaction.jdbc;


import com.luoyixin.www.orm.datasource.pool.PoolDataSource;
import com.luoyixin.www.orm.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.transaction
 * @ClassName: MyTransaction
 * @create 2021/4/23-21:25
 * @Version: 1.0
 */
public class JdbcTransaction implements Transaction {

    private static ThreadLocal<Connection> threadLocal = new InheritableThreadLocal<>();
    /**
     * 连接池
     */
    private PoolDataSource dataSource;
    /**
     * 事务级别
     */
    private Integer transactionLeve = Connection.TRANSACTION_REPEATABLE_READ;
    /**
     * 是否自动提交
     */
    private Boolean autoCommit = true;

    public JdbcTransaction(PoolDataSource poolDataSource) {
        this.dataSource = poolDataSource;
    }

    public JdbcTransaction(PoolDataSource poolDataSource,Integer transactionLeve, Boolean autoCommit) {
        this.dataSource = poolDataSource;
        this.transactionLeve = transactionLeve;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection()  {
        Connection connection;

        connection = threadLocal.get();
        //保证事务中是同一个连接
        if(connection != null) {
            return connection;
        }
        connection  = dataSource.getConnection();
        threadLocal.set(connection);

        try {
            //设置是否自动提交
            connection.setAutoCommit(autoCommit);
            //设置事务级别
            connection.setTransactionIsolation(transactionLeve);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    @Override
    public void commit() throws  SQLException {
        Connection connection = threadLocal.get();
        if(connection != null) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection != null) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection != null) {
            //避免连接池重新利用后出错
            connection.setAutoCommit(true);
            dataSource.releaseConnection(connection);
        }
        threadLocal.remove();
    }

    @Override
    public Boolean openTransaction() {
        return !autoCommit;
    }
}
