package com.luoyixin.www.orm.transaction.jdbc;


import com.luoyixin.www.orm.datasource.pool.PoolDataSource;
import com.luoyixin.www.orm.transaction.Transaction;
import com.luoyixin.www.orm.transaction.TransactionFactory;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.transaction.jdbc
 * @ClassName: JdbcTransactionFactory
 * @create 2021/4/25-16:54
 * @Version: 1.0
 */
public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(PoolDataSource dataSource, Integer level, boolean autoCommit) {
        return new JdbcTransaction(dataSource,level,autoCommit);
    }

    @Override
    public Transaction newTransaction(PoolDataSource dataSource) {
        return new JdbcTransaction(dataSource);
    }
}
