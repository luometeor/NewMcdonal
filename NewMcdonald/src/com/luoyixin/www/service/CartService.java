package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.Cart;
import com.luoyixin.www.po.CartItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: CartService
 * @create 2021/5/20-8:21
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.CartServiceImpl")
public interface CartService {
    /**
     * 根据用户id查询到购物车
     * @param userId
     * @return 没有的话返回null
     */
    List<Cart> queryCartByUserId(Integer userId);


    /**
     * 根据主键查询购物车
     * @param cartId 主键id
     * @return 返回购物车，没有的话为null
     */
    Cart queryCartByCartId(Integer cartId);

    /**
     * 根据用户id和店的id查询是否已经有购物车了
     * @param userId
     * @param storeId
     * @return
     */
    Cart queryCartByUserIdAndStoreId(Integer userId,Integer storeId);

    /**
     * 根据用户id 取消那个店的 购物车
     * 清空购物车
     * @param userId 用户id
     * @param storeId 店的id
     */
    @Transaction
    void removeCartAndCartItem(Integer userId,Integer storeId);

    /**
     * 当加入购物车时，没有购物车
     * 插入购物车并 加入 商品
     * @param cartItem 商品
     * @param userId 用户id
     * @param storeId 店的id
     * @return 返回新生成的的购物车
     */
    @Transaction
    Cart insertCartAndCartItem(CartItem cartItem, Integer userId, Integer storeId);

    /**
     * 当加入商品时，有购物车
     * 更新购物车并更新商品
     * @param cart
     * @param cartItem
     * @param userId
     * @param storeId
     * @return 返回更新好的购物车
     */
    @Transaction
    Cart updateCartAndCartItem(Cart cart,CartItem cartItem,Integer userId,Integer storeId);
}
