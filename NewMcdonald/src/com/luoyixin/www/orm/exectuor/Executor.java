package com.luoyixin.www.orm.exectuor;

import java.sql.SQLException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exectuor
 * @ClassName: Executor
 * @create 2021/4/26-14:01
 * @Version: 1.0
 */
public interface Executor {
    /**
     * 获取mapper对象
     * @param type class对象
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);

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
}
