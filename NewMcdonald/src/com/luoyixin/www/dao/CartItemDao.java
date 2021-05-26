package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.CartItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: CartItemDao
 * @create 2021/5/20-1:17
 * @Version: 1.0
 */
@Dao
public interface CartItemDao {
    /**
     * 更新cartItem
     * @param cartItem
     * @return
     */
    @Update
    int updateCartItem(CartItem cartItem);

    /**
     * 根据主键删除商品
     * @param cartItemId 主键
     * @return
     */
    @Delete("delete from cart_item where id  = ?")
    int deleteCartItemById(Integer cartItemId);

    /**
     * 增加cart中的cartItem
     * @param cartItem
     * @return
     */
    @Insert
    int addCartItem(CartItem cartItem);

    /**
     * 查询某个购物车的list
     * @param cartId
     * @return
     */
    @Select("select `id`,`item_name` itemName,`cool`,`hot`,`count`,`price`,`cart_id` cartId,`item_id` itemId,`total_price` totalPrice," +
            "`size` size from cart_item where cart_id = ?")
    List<CartItem> queryCartItemForList(Integer cartId);

    /**
     * 根据商品id（主键）查询到购物车的商品
     * @param cartItemId
     * @return
     */
    @Select("select `id`,`item_name` itemName,`cool`,`hot`,`count`,`price`,`cart_id` cartId,`item_id` itemId," +
            "`total_price` totalPrice,`size` size  from cart_item where id = ?")
    CartItem queryCartItemById(Integer cartItemId);
}
