package com.luoyixin.www.orm.statement;


import com.luoyixin.www.orm.config.MapperCore;
import com.luoyixin.www.orm.utils.SqlUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.statement
 * @ClassName: PreparedHander
 * @create 2021/4/30-12:00
 * @Version: 1.0
 */
public class PreparedStatementHandler {
    /**
     * 方法传入的参数
     */
    private Object[] args;

    private MapperCore mapperCore;

    private Connection connection;
    /**
     * 待执行的方法
     */
    private Method method;

    public PreparedStatementHandler(MapperCore mapperCore, Connection connection, Method method,Object[] args) {
        this.args = args;
        this.mapperCore = mapperCore;
        this.connection = connection;
        this.method = method;
    }

    /**
     * 产生sql语句,并且预编译sql语句
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPreparedStatement() throws SQLException {
        Boolean flag = true;
        String sql = mapperCore.getSql(method);

        Integer executeType = mapperCore.getExecuteType(method);
        if(executeType.equals(MapperCore.SELECT_TYPE)) {
            // 此时默认传入的是对象，注意不允许 传入对象后在传多一个参数，参数是对象的话就只能传一个参数
            //sql为空，那么就自动生成
           if("".equals(sql)) {
               Object object= (Object) args[0];
               sql = SqlUtils.selectSql(object.getClass(), args[0]);
               flag = false;
           }
        } else if(executeType.equals(MapperCore.UPDATE_TYPE)) {
            // 默认传入的是对象
            if("".equals(sql)) {
                Object object= (Object) args[0];
                sql = SqlUtils.updateSql(object.getClass(),args[0]);
                flag = false;
            }
        } else if(executeType.equals(MapperCore.INSERT_TYPE)) {
            // 默认传入的是对象
            if("".equals(sql)) {
                Object object= (Object) args[0];
                sql = SqlUtils.insertSql(object.getClass(),args[0]);
                flag = false;
            }
        } else {
            // 默认传入的是对象
            if("".equals(sql)) {
                Object object= (Object) args[0];
                sql = SqlUtils.deleteSql(object.getClass(),args[0]);
                flag = false;
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if(flag) {
            // 不是参对象，那么只能一一对应 传入参数
            for (int i = 0; args != null && i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
        }
       return preparedStatement;
    }
}
