package com.luoyixin.www.exception;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exception
 * @ClassName: InfomationException
 * @create 2021/5/21-15:12
 * @Version: 1.0
 */
public class InfomationException extends RuntimeException {

    private static final long serialVersionUID = -5262334L;

    public InfomationException() {

    }
    public InfomationException(String message) {
        super(message);
    }
}
