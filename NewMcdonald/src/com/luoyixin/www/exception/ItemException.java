package com.luoyixin.www.exception;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exception
 * @ClassName: ItemExcetpion
 * @create 2021/5/21-14:14
 * @Version: 1.0
 */
public class ItemException extends RuntimeException {
    private static final long serialVersionUID = -5251132334L;

    public ItemException() {

    }
    public ItemException(String message) {
        super(message);
    }
}
