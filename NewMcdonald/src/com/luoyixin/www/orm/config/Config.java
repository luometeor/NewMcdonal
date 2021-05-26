package com.luoyixin.www.orm.config;

import com.luoyixin.www.orm.datasource.pool.PoolDataSource;
import com.luoyixin.www.orm.exectuor.Executor;
import com.luoyixin.www.orm.exectuor.SimpleExecutor;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.core
 * @ClassName: config
 * @create 2021/4/23-20:24
 * @Version: 1.0
 */
public class Config {
    /**
     * dao文件路径
     */
    private String daoSource;
    /**
     * mapper的核心文件
     */
    private MapperCore mapperCore;
    /**
     * 数据源
     */
    private PoolDataSource poolDataSource;
    /**
     * 是否开启事务
     */
    private Boolean openTransaction = false;

    /**
     * 默认不开启事务
     * @param daoSource
     * @param poolDataSource
     */
    public Config(String daoSource, PoolDataSource poolDataSource) throws ClassNotFoundException {
        this.daoSource = daoSource;
        this.poolDataSource = poolDataSource;
        // 给mapperCore赋值
        mapperCore = new MapperCore(this);
    }

    public String getDaoSource() {
        return daoSource;
    }

    public void setDaoSource(String daoSource) {
        this.daoSource = daoSource;
    }

    public MapperCore getMapperCore() {
        return mapperCore;
    }

    public void setMapperCore(MapperCore mapperCore) {
        this.mapperCore = mapperCore;
    }

    public PoolDataSource getPoolDataSource() {
        return poolDataSource;
    }

    public void setPoolDataSource(PoolDataSource poolDataSource) {
        this.poolDataSource = poolDataSource;
    }

    public Boolean getOpenTransaction() {
        return openTransaction;
    }

    public void setOpenTransaction(Boolean openTransaction) {
        this.openTransaction = openTransaction;
    }

    /**
     * 获取执行器
     * @return
     */
    public Executor getExecutor() {
        return new SimpleExecutor(this,openTransaction);
    }

}
