package com.luoyixin.www.service.impl;

import com.luoyixin.www.ioc.ioc.AnnotationConfigApplicationContext;
import com.luoyixin.www.orm.config.Config;
import com.luoyixin.www.orm.datasource.PoolDataSourceFactory;
import com.luoyixin.www.orm.datasource.pool.PoolDataSource;
import com.luoyixin.www.orm.datasource.pool.PoolDataSourceFactoryImpl;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.service.CartItemService;
import com.luoyixin.www.service.CartService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.test
 * @ClassName: CartItemServiceTest
 * @create 2021/5/23-15:04
 * @Version: 1.0
 */
public class CartItemServiceTest {


    static AnnotationConfigApplicationContext annotationConfigApplicationContext;

    static {
        try {
            //数据池工厂
            PoolDataSourceFactory dataSourceFactory = new PoolDataSourceFactoryImpl();
            //传入配置文件相关信息
            dataSourceFactory.setProperties("jdbc.properties");
            //造池
            PoolDataSource poolDataSource = dataSourceFactory.getPoolDataSource();
            //全局配置文件
            Config config = new Config("com.luoyixin.www.dao", poolDataSource);
            //获取ioc
            annotationConfigApplicationContext = new AnnotationConfigApplicationContext(config, "com.luoyixin.www.service","com.luoyixin.www.controller");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  static <E> E getBean(String beanName) {
        if(beanName != null) {
            return (E) annotationConfigApplicationContext.getBean(beanName);
        }
        return null;
    }


    private CartItemService cartItemService = getBean("cartItemService");
    private CartService cartService = getBean("cartService");
    @Test
    public void updateCartItem() {
        CartItem cartItem = cartItemService.queryCartItemById(25);
        cartItem.setCount(10);
        cartItemService.updateCartItem(cartItem);
        Object i = 10;
        Assert.assertEquals(i,cartItemService.queryCartItemById(25).getCount());
    }

    @Test
    public void deleteCartItemAndUpdateCartById() {
        Cart cart = cartService.queryCartByCartId(20);
        Object i = 2;
        Assert.assertEquals(i,cart.getTotalCount());
        cartItemService.deleteCartItemAndUpdateCartById(42);
        cart = cartService.queryCartByCartId(20);
        i = 1;
        Assert.assertEquals(i,cart.getTotalCount());
        Assert.assertNull(cartItemService.queryCartItemById(42));
    }

    @Test
    public void queryCartItemForList() {
        Assert.assertNotNull(cartItemService.queryCartItemForList(1));
    }

    @Test
    public void queryCartItemById() {
        CartItem cartItem = cartItemService.queryCartItemById(42);
        Object oo = 3;
        Assert.assertEquals(oo,cartItem.getCount());
        Assert.assertEquals("冰淇淋",cartItem.getItemName());
    }

    @Test
    public void updateCartItemCount() {
        CartItem cartItem = cartItemService.queryCartItemById(55);
        cartItemService.updateCartItemCount(cartItem.getId(),20,cartItem.getCartId());
        cartItem = cartItemService.queryCartItemById(55);
        Object o = 20;
       Assert.assertEquals(o,cartItem.getCount());
    }
}