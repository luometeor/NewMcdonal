package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.*;
import com.luoyixin.www.exception.CartException;
import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.*;
import com.luoyixin.www.service.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: OrderServiceImpl
 * @create 2021/5/20-21:00
 * @Version: 1.0
 */
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private StoreDao storeDao;

    @Override
    public String createOrder(Cart cart, Integer storeId) {
        String orderId = LocalDateTime.now() + ""+cart.getUserId();
        Order order = new Order(null, orderId, java.sql.Date.valueOf(LocalDate.now()),cart.getTotalPrice(),0,cart.getUserId(),storeId);
        orderDao.addOrder(order);
        //更新到本地上
        order = orderDao.queryOrderByOrderId(orderId);
        List<CartItem> cartItems = cartItemDao.queryCartItemForList(cart.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        Optional.ofNullable(cartItems)
                .orElseThrow(() -> new CartException(Constant.NOT_ITEMS_BUT_ACCOUNT));


        Store store = storeDao.queryStoreById(storeId);

        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem(null,cartItem.getItemName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),
                    orderId,cartItem.getCool(),cartItem.getHot(),cartItem.getSize(),store.getStoreName());
            //删除购物车中商品
            cartItemDao.deleteCartItemById(cartItem.getId());
            //生成订单
            orderItemDao.insertOrderItem(orderItem);
            //增加订单中的商品
            orderItems.add(orderItem);
        });

        //删除购物车
        cartDao.deleteCartByCartId(cart.getId());
        return orderId;
    }

    @Override
    public List<Order> queryOrdersByStoreId(Integer storeId) {
        return orderDao.queryOrdersByStoreId(storeId);
    }

    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public Integer updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {
        return orderDao.queryOrderByOrderId(orderId);
    }

    @Override
    public List<Order> queryOrdersByLimitTime(Integer userId,String type) {
        List<Order> list = null;
        if(Constant.THIS_WEEK.equals(type)) {
            list =  orderDao.queryOrderThisWeek(userId);
        } else if(Constant.THIS_MONTH.equals(type)){
            list = orderDao.queryOrderThisMonth(userId);
        } else if(Constant.THIS_DAY.equals(type)){
            list = orderDao.queryOrderThisDay(userId);
        } else if(Constant.THIS_YEAR.equals(type)){
            list = orderDao.queryOrderThisYear(userId);
        } else if(Constant.LAST_YEAR.equals(type)) {
            list = orderDao.queryOrderLastYear(userId);
        }
        return list;
    }

    @Override
    public List<Order> queryOrdersByStatus(Integer storeId, String type) {
        List<Order> list = null;
        if(Constant.NO_SELL.equals(type)) {
            list = orderDao.queryOrdersByStatus(storeId,0);
        } else {
            list = orderDao.queryOrdersByStatus(storeId,1);
        }
        return list;
    }

    @Override
    public List<Order> queryOrdersByLimitTimeByStoreId(Integer storeId, String type) {
        List<Order> list = null;
        if(Constant.THIS_WEEK.equals(type)) {
            list =  orderDao.queryOrderThisWeekByStoreId(storeId);
        } else if(Constant.THIS_MONTH.equals(type)){
            list = orderDao.queryOrderThisMonthByStoreId(storeId);
        } else if(Constant.THIS_DAY.equals(type)){
            list = orderDao.queryOrderThisDayByStoreId(storeId);
        }
        return list;
    }

}
