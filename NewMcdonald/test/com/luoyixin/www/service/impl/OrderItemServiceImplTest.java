package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.OrderItem;
import com.luoyixin.www.service.OrderItemService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: OrderItemServiceImplTest
 * @create 2021/5/23-23:42
 * @Version: 1.0
 */
public class OrderItemServiceImplTest {
    private OrderItemService orderItemService = CartItemServiceTest.getBean("orderItemService");

    @Test
    public void queryOrderItemsByOrderId() {
        List<OrderItem> orderItems = orderItemService.queryOrderItemsByOrderId("2021-05-21T22:34:10.2312");
        Assert.assertNotNull(orderItems);
    }

    @Test
    public void queryOrderItemsByStoreName() {
        Assert.assertNotNull(orderItemService.queryOrderItemsByStoreName("大爷来玩呀"));
    }
}