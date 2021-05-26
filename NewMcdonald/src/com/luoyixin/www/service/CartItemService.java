package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.CartItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: CartItemServier
 * @create 2021/5/20-1:33
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.CartItemServiceImpl")
public interface CartItemService {
    /**
     * 更新cartItem
     * @param cartItem
     */
    void updateCartItem(CartItem cartItem);

    /**
     * 根据主键删除商品
     * @param cartItemId 主键
     */
    @Transaction
    void deleteCartItemAndUpdateCartById(Integer cartItemId);


    /**
     * 根据cartId查询集合
     * @param cartId
     * @return
     */
    List<CartItem> queryCartItemForList(Integer cartId);



    /**
     * 根据caryItemId查询到 商品
     * @param cartItemId 商品id
     * @return
     */
    CartItem queryCartItemById(Integer cartItemId);

    /**
     * 更新购物车中商品数量
     * @param cartItemId 要更改的那个商品id
     * @param count 要更改的数量
     * @param cartId 在那个购物车中更改
     */
    @Transaction
    void updateCartItemCount(Integer cartItemId, Integer count, Integer cartId);
}
