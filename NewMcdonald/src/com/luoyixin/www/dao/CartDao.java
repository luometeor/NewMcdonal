package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.Cart;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: CartDao
 * @create 2021/5/20-1:25
 * @Version: 1.0
 */
@Dao
public interface CartDao {
    /**
     * 根据用户id查询到购物车
     * @param userId
     * @return 没有的话返回null
     */
    @Select("select `id`,`total_count` totalCount,`total_price` totalPrice ,`user_id` userId,`store_id` storeId from cart where user_id = ?")
    List<Cart> queryCartByUserId(Integer userId);

    /**
     * 根据主键删除购物车
     * @param cartId
     * @return
     */
    @Delete("delete from cart where id=?")
    Integer deleteCartByCartId(Integer cartId);

    /**
     * 更新购物车
     * @param cart
     * @return
     */
    @Update
    Integer updateCart(Cart cart);

    /**
     * 增加购物车
     * @param cart
     * @return
     */
    @Insert
    Integer addCart(Cart cart);

    /**
     * 根据主键查询 购物车
     * @param cartId
     * @return 返回购物车 没有的话为null
     */
    @Select("select `id`,`total_count` totalCount,`total_price` totalPrice ,`user_id` userId,`store_id` storeId from cart where id = ?")
    Cart queryCartByCartId(Integer cartId);

    /**
     * 根据用户id和店的id查询
     * @param storeId
     * @param userId
     * @return
     */
    @Select("select `id`,`total_count` totalCount,`total_price` totalPrice ,`user_id` userId,`store_id` storeId from cart where user_id = ? and store_id = ?")
    Cart queryCartByUserIdAndStoreId(Integer userId,Integer storeId);
}
