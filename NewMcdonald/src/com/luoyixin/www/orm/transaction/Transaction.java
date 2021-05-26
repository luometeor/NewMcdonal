package com.luoyixin.www.orm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.transaction
 * @ClassName: Transaction
 * @create 2021/4/22-22:01
 * @Version: 1.0
 */

public interface Transaction {
    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * 提交
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * 回滚
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * 关闭
     * @throws SQLException
     */
    void close() throws SQLException;

    /**
     * 是否开启了事务
     * @return 开启事务返回true，否则未false
     */
    Boolean openTransaction();
}
