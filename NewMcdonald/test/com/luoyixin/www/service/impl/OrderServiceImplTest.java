package com.luoyixin.www.service.impl;

import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.po.Order;
import com.luoyixin.www.service.CartItemService;
import com.luoyixin.www.service.CartService;
import com.luoyixin.www.service.OrderService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.test
 * @ClassName: OrderServiceImplTest
 * @create 2021/5/23-15:41
 * @Version: 1.0
 */
public class OrderServiceImplTest {

    OrderService orderService = CartItemServiceTest.getBean("orderService");
    CartService cartService = CartItemServiceTest.getBean("cartService");
    CartItemService cartItemService = CartItemServiceTest.getBean("cartItemService");
    @Test
    public void createOrder() {
        Cart cart = cartService.queryCartByCartId(21);
        List<CartItem> cartItems = cartItemService.queryCartItemForList(cart.getId());
        Assert.assertEquals(1,cartItems.size());
        orderService.createOrder(cart,8);
        cartItems = cartItemService.queryCartItemForList(cart.getId());
        Assert.assertEquals(0,cartItems.size());
    }

    @Test
    public void queryOrdersByStoreId() {
        List<Order> list = orderService.queryOrdersByStoreId(8);
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void queryOrdersByUserId() {
        Assert.assertEquals(5,orderService.queryOrdersByUserId(2).size());
        List<Order> list = orderService.queryOrdersByUserId(3);
       Assert.assertEquals(0,list.size());
    }



    @Test
    public void queryOrderByOrderId() {
        Assert.assertNotNull( orderService.queryOrderByOrderId("2021-05-23T15:12:09.0492"));
        Order order = orderService.queryOrderByOrderId("1");
       Assert.assertNull(order);
    }

    @Test
    public void queryOrdersByLimitTime() {
        Assert.assertEquals(2,orderService.queryOrdersByLimitTime(2, "this_week").size());
        Assert.assertEquals(3,orderService.queryOrdersByLimitTime(2,"this_month").size());
        Assert.assertEquals(4,orderService.queryOrdersByLimitTime(2,"this_year").size());
        Assert.assertEquals(0,orderService.queryOrdersByLimitTime(2, "this_day").size());
        Assert.assertEquals(1,orderService.queryOrdersByLimitTime(2,Constant.LAST_YEAR).size());
    }

    @Test
    public void queryOrdersByStatus() {
        Assert.assertEquals(2,orderService.queryOrdersByStatus(8, Constant.NO_SELL).size());
        Assert.assertEquals(2,orderService.queryOrdersByStatus(8,"SELL").size());
    }

    @Test
    public void queryOrdersByLimitTimeByStoreId() {
        Assert.assertEquals(0,orderService.queryOrdersByLimitTimeByStoreId(8, "this_day").size());
        Assert.assertEquals(3,orderService.queryOrdersByLimitTimeByStoreId(8, "this_week").size());
        Assert.assertEquals(3,orderService.queryOrdersByLimitTimeByStoreId(8, "this_month").size());
    }

    @Test
    public void updateOrder() {
        Order order = orderService.queryOrderByOrderId("2021-05-23T15:12:09.0492");
        order.setStatus(0);
        Object i = 1;
        Assert.assertEquals(i,orderService.updateOrder(order));
    }
}