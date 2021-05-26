package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.po.OrderItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: OrderItemService
 * @create 2021/5/20-21:03
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.OrderItemServiceImpl")
public interface OrderItemService {

    /**
     * 根据订单号查询商品
     * @param orderId
     * @return
     */
    List<OrderItem> queryOrderItemsByOrderId(String orderId);

    /**
     * 查询到一个店中所有的商品
     * @param storeName
     * @return
     */
    List<OrderItem> queryOrderItemsByStoreName(String storeName);
}
