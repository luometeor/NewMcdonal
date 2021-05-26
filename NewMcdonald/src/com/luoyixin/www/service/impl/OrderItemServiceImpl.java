package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.OrderItemDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.OrderItem;
import com.luoyixin.www.service.OrderItemService;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: OrderItemServiceImpl
 * @create 2021/5/20-21:05
 * @Version: 1.0
 */
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        return orderItemDao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public List<OrderItem> queryOrderItemsByStoreName(String storeName) {
        return orderItemDao.queryOrderItemsByStoreName(storeName);
    }
}
