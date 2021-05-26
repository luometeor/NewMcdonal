package com.luoyixin.www.orm.datasource;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.datasource
 * @ClassName: PoolDataSourceException
 * @create 2021/4/25-17:36
 * @Version: 1.0
 */
public class PoolDataSourceException extends RuntimeException{
    private static final long serialVersionUID = -52513962334L;

    public PoolDataSourceException() {
    }

    public PoolDataSourceException(String message) {
        super(message);
    }


}
