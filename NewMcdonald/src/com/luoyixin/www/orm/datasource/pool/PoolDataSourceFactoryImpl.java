package com.luoyixin.www.orm.datasource.pool;

import com.luoyixin.www.orm.datasource.PoolDataSourceException;
import com.luoyixin.www.orm.datasource.PoolDataSourceFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource.pool
 * @ClassName: PoolDataSourceFactory
 * @create 2021/4/25-17:35
 * @Version: 1.0
 */
public class PoolDataSourceFactoryImpl implements PoolDataSourceFactory {
    private Properties properties;
    private String userName;
    private String password;
    private String driver;
    private String url;
    private Integer maxActiveConnection;
    private Integer maxIdleConnection;

    @Override
    public void setProperties(String path)  {
        properties = new Properties();
        InputStream resourceAsStream = PoolDataSourceFactoryImpl.class.getClassLoader().getResourceAsStream(path);
        try {
            properties.load(resourceAsStream);
        } catch (Exception e ) {
            throw new PoolDataSourceException("配置文件路径出错");
        }
        url =  properties.getProperty("url");
        userName = properties.getProperty("username");
        password = properties.getProperty("password");
        driver = properties.getProperty("driverClassName");
        try {
            maxActiveConnection = Integer.valueOf(properties.getProperty("maxActiveConnection","10"));
            maxIdleConnection = Integer.valueOf(properties.getProperty("maxIdleConnection","10"));
        } catch (Exception e) {
            throw new PoolDataSourceException("配置文件的，maxActiveConnection或者maxIdleConnection要输入数字");
        }
    }

    @Override
    public PoolDataSource getPoolDataSource() {
        return new PoolDataSource(driver,url,userName,password,maxActiveConnection,maxIdleConnection);
    }
}
