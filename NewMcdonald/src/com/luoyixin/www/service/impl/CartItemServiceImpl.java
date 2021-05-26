package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.CartDao;
import com.luoyixin.www.dao.CartItemDao;
import com.luoyixin.www.dao.PriceSizeDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.service.CartItemService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: CartItemServiceImpl
 * @create 2021/5/20-8:03
 * @Version: 1.0
 */
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private PriceSizeDao priceSizeDao;
    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem);
    }

    @Override
    public void deleteCartItemAndUpdateCartById(Integer cartItemId) {
        CartItem cartItem = cartItemDao.queryCartItemById(cartItemId);

        Cart cart = cartDao.queryCartByCartId(cartItem.getCartId());

        cartItemDao.deleteCartItemById(cartItemId);
        if(cart.getTotalCount()-cartItem.getCount() == 0) {
            cartDao.deleteCartByCartId(cart.getId());
        } else {
            //购物车中的总数
            cart.setTotalCount(cart.getTotalCount()-cartItem.getCount());
            //购物车中的总价格
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getTotalPrice()));

            cartDao.updateCart(cart);
        }

        cartItemDao.deleteCartItemById(cartItemId);

    }


    @Override
    public List<CartItem> queryCartItemForList(Integer cartId) {
        return cartItemDao.queryCartItemForList(cartId);
    }



    @Override
    public CartItem queryCartItemById(Integer cartItemId) {

        return cartItemDao.queryCartItemById(cartItemId);
    }


    @Override
    public void updateCartItemCount(Integer cartItemId, Integer count, Integer cartId) {
        CartItem cartItem = cartItemDao.queryCartItemById(cartItemId);
        //以前的数据
        Integer previousCount = cartItem.getCount();
        BigDecimal previousTotalPrice = cartItem.getTotalPrice();
        //设置新的数据然后更新到数据库
        cartItem.setCount(count);
        cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(count)));
        cartItemDao.updateCartItem(cartItem);
        //改变购物车的值
        Cart cart = cartDao.queryCartByCartId(cartId);
        //加上现在的数据，减去以前的数据
        cart.setTotalCount(cart.getTotalCount()+count-previousCount);
        BigDecimal diff = cartItem.getTotalPrice().subtract(previousTotalPrice);
        cart.setTotalPrice(cart.getTotalPrice().add(diff));
        cartDao.updateCart(cart);
    }
}
