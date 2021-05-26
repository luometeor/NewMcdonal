package com.luoyixin.www.orm.exception;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exception
 * @ClassName: NormalException
 * @create 2021/4/26-11:56
 * @Version: 1.0
 */
public class NormalException extends RuntimeException{
    private static final long serialVersionUID = -52513962334L;

    public NormalException() {
    }

    public NormalException(String message) {
        super(message);
    }

}
