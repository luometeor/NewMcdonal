package com.luoyixin.www.orm.datasource.pool;


import java.sql.Connection;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource
 * @ClassName: PoolDataSource
 * @create 2021/4/23-21:35
 * @Version: 1.0
 */
public class PoolDataSource  {

    /**
     * 最大活跃线程数
     */
    private Integer maxActiveConnection = 10;

    /**
     * 最大空闲线程数
     */
    private Integer maxIdleConnection = 10;

    /**
     * 连接最长使用时间，30秒
     */
    private Long maxConnectTime = 30*1000L;

    /**
     * 最长等待时间
     */
    private Long waitTime = 2*1000L;

    private NormalDataSource normalDataSource;

    /**
     * 正在的连接
     */
    private Queue<PooledConnection> activeConnection = new LinkedList<>();

    /**
     *空闲的连接
     */
    private Queue<PooledConnection> idleConnection = new LinkedList<>();

    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


    public PoolDataSource(String driverClassName,String url,String userName,String password,Integer maxActiveConnection,Integer maxIdleConnection) {
        this.normalDataSource = new NormalDataSource(driverClassName,password,url,userName);
        this.maxActiveConnection = maxActiveConnection;
        this.maxIdleConnection = maxIdleConnection;
    }

    /**
     * 获取连接
     * @return
     */
    public Connection getConnection() {
        return getPooledConnection().connection;
    }

    /**
     * 释放资源
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        PooledConnection pooledConnection = new PooledConnection(connection);
        lock.lock();
        try {
            if(idleConnection.size() < maxIdleConnection) {
                //重新利用
                idleConnection.add(pooledConnection);
                //唤醒线程
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }


    /**
     * 获取设有开始连接时间
     * @return
     */
    private PooledConnection getPooledConnection() {
        PooledConnection connection = null;
        lock.lock();
        try {
            //唤醒后继续跑循环，因为唤醒时可能有线程拿到锁，idleConnection被拿了
            while(connection == null) {
                if (idleConnection.size() <= 0) {
                    if (activeConnection.size() < maxActiveConnection) {
                        connection = new PooledConnection(normalDataSource.getConnection());
                    } else {
                        //获取第一个连接
                        PooledConnection pooledConnection = activeConnection.peek();
                        //判断是否超时
                        if (Instant.now().toEpochMilli() - pooledConnection.getCheckOutTime() > maxConnectTime) {
                            // 移除第一个活跃连接
                            PooledConnection timeOutConnection = activeConnection.poll();
                            if (!timeOutConnection.connection.getAutoCommit()) {
                                //回滚， 用户操作失效
                                timeOutConnection.connection.rollback();
                            }
                        } else {
                            //唤醒后继续跑循环，因为唤醒时可能有线程拿到锁，idleConnection被拿了
                            condition.await(waitTime, TimeUnit.MILLISECONDS);
                        }
                    }
                } else {
                    // 获取第一个空闲连接
                    connection = idleConnection.poll();
                }
                if (connection != null) {
                    //设置开始连接时间
                    connection.setCheckOutTime(Instant.now().toEpochMilli());
                    activeConnection.add(connection);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return connection;
    }


}
