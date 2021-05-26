package com.luoyixin.www.exception;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.exception
 * @ClassName: Constant
 * @create 2021/5/21-11:44
 * @Version: 1.0
 */
public class Constant {
    /**
     * 用于报错
     */
    public static final String CART_NO_FIND = "没有购物车，但是发出清空购物车请求";
    public static final String PARAM_NO_MATCH = "增加商品时，前端传过来的参数类型不匹配";
    public static final String ITEM_WITHOUT_PRICE = "商品没有属性，比如价格等等";
    public static final String DESTINATION_WITHOUT_DATA = "前端传入的数据destination缺少信息";
    public static final String NOT_ITEMS_BUT_ACCOUNT = "购物车没有数据，但是却结账了";
    public static final String ClASS_NO_FIND ="class对象没有找到";


    //不用于报错
    /**
     * 价格
     */
    public static final String PRICE = "price";


    /**
     * 这个月
     */
    public static final String THIS_MONTH = "this_month";
    /**
     * 上个月
     */
    public static final String THIS_WEEK = "this_week";
    /**
     * 今年
     */
    public static final String THIS_YEAR = "this_year";
    /**
     * 上一年
     */
    public static final String LAST_YEAR = "last_year";
    /**
     * 当天
     */
    public static final String THIS_DAY = "this_day";
    /**
     * 查询所有
     */
    public static final String ALL = "all";
    /**
     * 还未出单
     */
    public static final String NO_SELL = "no_sell";

    /**
     * 字符串常量Request
     */
    public static final CharSequence REQUEST = "Request";
    /**
     * 字符串常量Respond
     */
    public static final CharSequence RESPONSE = "Response";
    /**
     * 数字3
     */
    public static final int THREE = 3;
    /**
     * 一页数字
     */
    public static final Integer PAGE_SIZE = 4;
    /**
     * 实现类
     */
    public static final String IMPL = "Impl";


}
