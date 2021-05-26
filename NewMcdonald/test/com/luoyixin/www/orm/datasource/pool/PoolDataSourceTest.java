package com.luoyixin.www.orm.datasource.pool;

import com.luoyixin.www.orm.datasource.PoolDataSourceException;
import com.luoyixin.www.orm.datasource.PoolDataSourceFactory;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.orm.datasource.pool
 * @ClassName: PoolDataSourceTest
 * @create 2021/5/24-8:58
 * @Version: 1.0
 */
public class PoolDataSourceTest {
    static  PoolDataSource poolDataSource =null;
    static
    {
        PoolDataSourceFactory dataSourceFactory = new PoolDataSourceFactoryImpl();
        //传入配置文件相关信息
        try {
            dataSourceFactory.setProperties("jdbc.properties");
        } catch (PoolDataSourceException e) {
            e.printStackTrace();
        }
        //造池
         poolDataSource = dataSourceFactory.getPoolDataSource();
    }
    //数据池工厂

    @Test
    public void getConnection() {
        for (int i = 0; i < 15; i++) {
            Connection connection = poolDataSource.getConnection();
        }
    }

    @Test
    public void releaseConnection() {
    }
}