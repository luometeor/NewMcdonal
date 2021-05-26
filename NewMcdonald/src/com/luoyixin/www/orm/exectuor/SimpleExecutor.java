package com.luoyixin.www.orm.exectuor;


import com.luoyixin.www.orm.cache.Cache;
import com.luoyixin.www.orm.cache.SimpleCache;
import com.luoyixin.www.orm.config.Config;
import com.luoyixin.www.orm.config.MapperCore;
import com.luoyixin.www.orm.mapper.ProxyFactory;
import com.luoyixin.www.orm.statement.PreparedStatementHandler;
import com.luoyixin.www.orm.transaction.Transaction;
import com.luoyixin.www.orm.transaction.jdbc.JdbcTransactionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exectuor
 * @ClassName: SimpleExecutor
 * @create 2021/4/30-11:44
 * @Version: 1.0
 */
public class SimpleExecutor implements Executor{

    public Transaction transaction;
    /**
     * 执行需要mapper信息
     */
    public MapperCore mapperCore;
    /**
     * 缓存
     */
    private Cache cache ;

    private Config config;

    JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
    /**
     * 保存2个开启事务的Transaction与未开启事务的Transaction
     */
    private Transaction openTransactions;
    private Transaction notOpenTransactions ;

    public SimpleExecutor(Config config,Boolean openTransaction) {
        this.config = config;
        this.mapperCore = config.getMapperCore();
        if(openTransaction) {
            this.transaction = jdbcTransactionFactory.newTransaction(config.getPoolDataSource(),Connection.TRANSACTION_REPEATABLE_READ,false);
            openTransactions = this.transaction;
            notOpenTransactions = jdbcTransactionFactory.newTransaction(config.getPoolDataSource());
        } else {
            this.transaction = jdbcTransactionFactory.newTransaction(config.getPoolDataSource());
            openTransactions = jdbcTransactionFactory.newTransaction(config.getPoolDataSource(),Connection.TRANSACTION_REPEATABLE_READ,false);
            notOpenTransactions = this.transaction;
        }
        cache = new SimpleCache();
    }

    /**
     * 给执行器设置是否开启事务
     * @param openTransaction
     */
    public void setOpenTransaction(Boolean openTransaction) {
        //要开启事务 并且当前未开启事务
        if(openTransaction && !this.transaction.openTransaction() ) {
            this.transaction = openTransactions;
        }
        //不开启事务 并且当前开启了
        else if(!openTransaction && this.transaction.openTransaction()) {
            this.transaction = notOpenTransactions;
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
       return new ProxyFactory<T>(type, this).newInstance();
    }


    /**
     * 执行查询操作
     * @param method 执行的方法
     * @param args  传进的参数
     * @param <E>
     * @return
     * @throws Exception
     */
   public <E> List<E> select(Method method, Object[] args) throws Exception{
       String key = generateCacheKey(method, args);
       //存在缓冲，不需要再次发送sql语句
       if(cache.getCache(key) != null) {
           return (List<E>) cache.getCache(key);
       }
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
       try {
           PreparedStatementHandler preparedStatementHandle = new PreparedStatementHandler(mapperCore,transaction.getConnection(),method,args);
           //得到预编译好的preparedStatement
           preparedStatement = preparedStatementHandle.createPreparedStatement();
           resultSet = preparedStatement.executeQuery();
           //处理结果集
           ResultSetMetaData metaData = resultSet.getMetaData();
           Class<E> returnClass = (Class<E>) mapperCore.getReturnType(method);
           int columnCount = metaData.getColumnCount();
           List<E> arrayList = new ArrayList<>();
           while (resultSet.next()) {
               E e = returnClass.newInstance();
               for (int i = 0; i < columnCount; i++) {
                   Object columnValue = resultSet.getObject(i + 1);
                   String columnLabel = metaData.getColumnLabel(i + 1);
                   Field field = e.getClass().getDeclaredField(columnLabel);
                   field.setAccessible(true);
                   field.set(e, columnValue);
               }
               arrayList.add(e);
           }
           //加入缓冲
           if(arrayList.size() != 0 ){
               cache.putCache(key,arrayList);
           }

           return arrayList;
       } finally {
           if(resultSet != null) {
               resultSet.close();
           }
           if(preparedStatement != null) {
               preparedStatement.close();
           }
           if(!this.transaction.openTransaction()) {
               this.transaction.close();
           }
       }
   }

    /**
     *  查询特殊值
     * @param method 要执行的方法
     * @param args 传进来的参数
     * @return
     * @throws SQLException
     */
    public Object getValue(Method method, Object... args) throws SQLException {
        String key = generateCacheKey(method, args);
        //存在缓冲，不需要再次发送sql语句
        if(cache.getCache(key) != null) {
            return cache.getCache(key);
        }
        PreparedStatementHandler preparedStatementHandle = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatementHandle = new PreparedStatementHandler(mapperCore, transaction.getConnection(), method, args);
            preparedStatement = preparedStatementHandle.createPreparedStatement();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Object object = resultSet.getObject(1);
                //加入缓冲
                cache.putCache(key,object);
                return object;
            }
        } finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(!this.transaction.openTransaction()) {
                this.transaction.close();
            }
        }
        return null;
    }




    /**
     *  执行 增删改操作
     * @param method
     * @param args
     * @return
     * @throws Exception
     */
    public Integer update(Method method,Object[] args) throws Exception {
        // 可能修改或者删除了数据 清空缓冲
        cache.clearCache();
        PreparedStatementHandler preparedStatementHandle;
        PreparedStatement preparedStatement = null;
        Integer count = 0;
        try{
            preparedStatementHandle = new PreparedStatementHandler(mapperCore,transaction.getConnection(),method,args);
            preparedStatement = preparedStatementHandle.createPreparedStatement();
            count = preparedStatement.executeUpdate();
        }finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(!this.transaction.openTransaction()) {
                this.transaction.close();
            }
        }

        return count;
    }

    @Override
    public void commit() throws SQLException {
        transaction.commit();
    }

    @Override
    public void rollback() throws SQLException {
        transaction.rollback();
    }

    @Override
    public void close() throws SQLException {
        transaction.close();
    }

    /**
     *  生成缓冲key
     * @param method
     * @param args
     * @return
     */
    private String generateCacheKey(Method method, Object[] args){
        StringBuilder cacheKey = new StringBuilder();
        //方法有可能重载
        cacheKey.append(method.getName());

        Optional.ofNullable(args)
                .filter(arg -> arg.length != 0)
                .ifPresent(arg -> {
                    Arrays.asList(arg).forEach(o -> {
                        cacheKey.append(o.toString());
                    });
                });

        return cacheKey.toString();
    }

}
