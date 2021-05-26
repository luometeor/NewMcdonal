package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.CartDao;
import com.luoyixin.www.dao.CartItemDao;
import com.luoyixin.www.dao.PriceSizeDao;
import com.luoyixin.www.exception.CartException;
import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.manager.TransactionStatus;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.po.PriceSize;
import com.luoyixin.www.service.CartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: CartServiceImpl
 * @create 2021/5/20-8:21
 * @Version: 1.0
 */
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private PriceSizeDao priceSizeDao;

    @Override
    public List<Cart> queryCartByUserId(Integer userId) {
        return cartDao.queryCartByUserId(userId);
    }


    @Override
    public Cart queryCartByCartId(Integer cartId) {
        return cartDao.queryCartByCartId(cartId);
    }

    @Override
    public Cart queryCartByUserIdAndStoreId(Integer userId, Integer storeId) {
        return cartDao.queryCartByUserIdAndStoreId(userId,storeId);
    }

    @Override
    public void removeCartAndCartItem(Integer userId, Integer storeId) {
        //找到cart
        Cart cart = cartDao.queryCartByUserIdAndStoreId(userId,storeId);

        if(cart == null) {
            throw new CartException(Constant.CART_NO_FIND);
        }
        //找到购物车中的商品
        List<CartItem> cartItems = cartItemDao.queryCartItemForList(cart.getId());

        Optional.ofNullable(cartItems)
                .filter(cartItem -> !cartItem.isEmpty())
                .ifPresent(cartItem -> {
                    cartItem.forEach(item -> {
                        cartItemDao.deleteCartItemById(item.getId());
                    });
                });

        //删除购物车
        cartDao.deleteCartByCartId(cart.getId());

    }


    @Override
    public Cart insertCartAndCartItem(CartItem cartItem,Integer userId,Integer storeId) {
        Cart cart = new Cart(null,1,cartItem.getPrice(),userId,storeId);
        cartDao.addCart(cart);
        //把id更新到本地上
        cart = cartDao.queryCartByUserIdAndStoreId(userId,storeId);
        //完善信息后添加
        addCartItem(cart,cartItem);
        return cart;
    }

    @Override
    public Cart updateCartAndCartItem(Cart cart,CartItem cartItem,Integer userId,Integer storeId) {
        Integer cartItemId = existCartItem(userId, cartItem.getItemId(), cartItem.getPrice(),storeId);
        //存在cartItem那个商品商品了
        if(cartItemId != 0) {
            CartItem existCartItem = cartItemDao.queryCartItemById(cartItemId);
            existCartItem.setCount(existCartItem.getCount()+1);
            existCartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(existCartItem.getCount())));
            cartItemDao.updateCartItem(existCartItem);
        } else {
            //完善信息后添加
            addCartItem(cart,cartItem);
        }
        //更新cart
        cart.setTotalCount(cart.getTotalCount()+1);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice()));
        cartDao.updateCart(cart);
        return cart;
    }



    /**
     * 增加cartItem到购物车中
     * @param cart
     * @param cartItem
     */
    private void addCartItem(Cart cart,CartItem cartItem) {
        cartItem.setCount(1);
        cartItem.setCartId(cart.getId());
        cartItem.setTotalPrice(cartItem.getPrice());
        setCartItemSize(cartItem);
        cartItemDao.addCartItem(cartItem);
    }


    /**
     * 根据价格判断size,套餐的话不管
     * @param cartItem
     */
    private void setCartItemSize(CartItem cartItem) {
        BigDecimal price = cartItem.getPrice();
        Integer itemId = cartItem.getItemId();
        List<PriceSize> priceSizeByItem = priceSizeDao.queryPriceSizeByItemId(itemId);

        if(priceSizeByItem.size() > 1) {

            priceSizeByItem.forEach(priceSize -> {
                if (priceSize.getPrice().compareTo(price) == 0) {
                    cartItem.setSize(priceSize.getSize());
                }
            });
        }
    }

    /**
     * 根据userId定位购物车，根据itemId查看该购物车是否有item了
     * @param userId 用户id
     * @param itemId 商品id
     * @param price 要添加的商品的价格
     * @param storeId 店的id
     * @return 返回已经存在的id，如果为0表示不存在
     */
    private Integer existCartItem(Integer userId, Integer itemId, BigDecimal price,Integer storeId) {
        Cart cart = cartDao.queryCartByUserIdAndStoreId(userId,storeId);
        List<PriceSize> priceSizes = priceSizeDao.queryPriceSizeByItemId(itemId);
        //已经存在的
        List<CartItem> cartItems = cartItemDao.queryCartItemForList(cart.getId());

        for (CartItem cartItem : cartItems) {
            if(itemId.compareTo(cartItem.getItemId()) == 0  ) {
                //价格也相同，因为可能存在大小份的区别
                if(cartItem.getPrice().compareTo(price) == 0) {
                    return cartItem.getId();
                }
            }
        }

        return 0;
    }
}
