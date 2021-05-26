package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.Order;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: OrderService
 * @create 2021/5/20-20:57
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.OrderServiceImpl")
public interface OrderService {
    /**
     * 根据购物车生成
     * 生成订单
     * @param cart 购物车
     * @param storeId 店的id
     * @return
     */
    @Transaction
    String createOrder(Cart cart,Integer storeId);

    /**
     * 给店长用的
     * 根据店的id查询所有order
     * @param storeId 店的id
     * @return
     */
    List<Order> queryOrdersByStoreId(Integer storeId);

    /**
     * 根据用户id查询 所有list
     * @param userId
     * @return 没有的话返回空的集合
     */
    List<Order> queryOrdersByUserId(Integer userId);

    /**
     * 更新order
     * @param order
     * @return
     */
    Integer updateOrder(Order order);

    /**
     * 根据 订单号查询
     * @param orderId
     * @return
     */
    Order queryOrderByOrderId(String orderId);

    /**
     * 查询某个用户 的某个时间范围内的数据
     * @param userId 用户id
     * @param type 类型 -->一周前 一个月前
     * @return
     */
    List<Order> queryOrdersByLimitTime(Integer userId,String type);

    /**
     * 根据订单状态查询
     * @param storeId
     * @param type NO_SELL 还没出单 SELL 出单
     * @return
     */
    List<Order> queryOrdersByStatus(Integer storeId, String type);

    /**
     * 查询某个店 的某个时间范围内的数据
     * @param storeId 店id
     * @param type 类型 -->一周前 一个月前
     * @return 返回空的集合
     */
    List<Order> queryOrdersByLimitTimeByStoreId(Integer storeId, String type);
}
