package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.Dao;
import com.luoyixin.www.orm.annotation.Insert;
import com.luoyixin.www.orm.annotation.Select;
import com.luoyixin.www.po.OrderItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: OrderItemDao
 * @create 2021/5/20-20:56
 * @Version: 1.0
 */
@Dao
public interface OrderItemDao {
    /**
     * 根据订单查询 所有商品
     * @param orderId 订单号
     * @return
     */
    @Select("select `id`,`item_name` itemName,`count`,`price`,`total_price` totalPrice,`order_id` orderId,`cool`,`hot`,`size`,`store_name` storeName from t_order_item where order_id = ? ")
    List<OrderItem>  queryOrderItemsByOrderId(String orderId);

    /**
     * 插入orderItem
     * @param orderItem
     * @return
     */
    @Insert
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 根据店名查询到所有的商品
     * @param storeName
     * @return
     */
    @Select("select `id`,`item_name` itemName,`count`,`price`,`total_price` totalPrice,`order_id` orderId,`cool`,`hot`,`size`,`store_name` storeName from t_order_item where store_name = ? ")
    List<OrderItem> queryOrderItemsByStoreName(String storeName);
}
