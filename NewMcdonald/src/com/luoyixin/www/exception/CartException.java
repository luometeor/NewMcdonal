package com.luoyixin.www.exception;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exception
 * @ClassName: CartException
 * @create 2021/5/21-11:42
 * @Version: 1.0
 */
public class CartException extends RuntimeException{
    private static final long serialVersionUID = -525162334L;

    public CartException() {
    }

    public CartException(String message) {
        super(message);
    }
}
