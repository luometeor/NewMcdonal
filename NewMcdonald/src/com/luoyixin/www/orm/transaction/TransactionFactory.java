package com.luoyixin.www.orm.transaction;


import com.luoyixin.www.orm.datasource.pool.PoolDataSource;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.transaction
 * @ClassName: JdbcTransactionFactory
 * @create 2021/4/25-16:50
 * @Version: 1.0
 */
public interface TransactionFactory {

    /**
     * 创建一个 事务对象
     * @param dataSource 数据连接池
     * @param level 事务等级
     * @param autoCommit 是否自动提交
     * @return
     */
    Transaction newTransaction(PoolDataSource dataSource, Integer level, boolean autoCommit);

    /**
     * 创建一个 事务对象
     * @param dataSource 数据连接池
     * @return
     */
    Transaction newTransaction(PoolDataSource dataSource);




}
