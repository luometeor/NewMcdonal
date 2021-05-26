package com.luoyixin.www.orm.datasource;


import com.luoyixin.www.orm.datasource.pool.PoolDataSource;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource
 * @ClassName: PoolDataSourceFactory
 * @create 2021/4/25-17:32
 * @Version: 1.0
 */
public interface PoolDataSourceFactory {
    /**
     * 设置配置文件
     * @param path 路径
     * @throws PoolDataSourceException
     */
    void setProperties(String path) ;

    /**
     * 得到连接池
     * @return
     */
    PoolDataSource getPoolDataSource();
}
