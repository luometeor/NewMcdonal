package com.luoyixin.www.service.impl;


import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.service.CartService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www
 * @ClassName: CartServiceTest
 * @create 2021/5/23-19:41
 * @Version: 1.0
 */
public class CartServiceTest {

    CartService cartService = CartItemServiceTest.getBean("cartService");
    @Test
    public void queryCartByUserId() {
       Assert.assertNotNull(cartService.queryCartByUserId(1));
    }


    @Test
    public void queryCartByCartId() {
        Assert.assertNotNull(cartService.queryCartByCartId(10));
    }

    @Test
    public void queryCartByUserIdAndStoreId() {
        Assert.assertNotNull(cartService.queryCartByUserIdAndStoreId(1,8));
    }


    @Test
    public void insertCartAndCartItem() {
        cartService.insertCartAndCartItem(new CartItem(null,"全家桶","","",1,new BigDecimal("99.99"),10,41,new BigDecimal("99.99"),""),1,8);
        Cart cart = cartService.queryCartByUserIdAndStoreId(1, 8);
        Assert.assertEquals(new BigDecimal("99.99"),cart.getTotalPrice());
    }

    @Test
    public void removeCartAndCartItem() {
        cartService.removeCartAndCartItem(1,8);
        Assert.assertNull(cartService.queryCartByUserIdAndStoreId(1,8));
    }

    @Test
    public void updateCartAndCartItem() {
        Cart cart = new Cart();
        cartService.updateCartAndCartItem(cart,new CartItem(null,"全家桶","","",1,new BigDecimal("99.99"),10,41,new BigDecimal("99.99"),""),1,8);
        cart = cartService.queryCartByUserIdAndStoreId(1, 8);
       Assert.assertEquals(new BigDecimal("99.99"),cart.getTotalPrice());
    }





}
