package com.luoyixin.www.orm.datasource.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource
 * @ClassName: NormalDataSource
 * @create 2021/4/24-18:59
 * @Version: 1.0
 */
public class NormalDataSource {
    private String driverClassName;

    private String password;

    private String url;

    private String userName;

    public NormalDataSource() {
    }

    public NormalDataSource(String driverClassName, String password, String url, String userName) {
        this.driverClassName = driverClassName;
        this.password = password;
        this.url = url;
        this.userName = userName;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        return DriverManager.getConnection(url,userName,password);
    }



}
