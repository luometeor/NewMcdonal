package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.Dao;
import com.luoyixin.www.orm.annotation.Insert;
import com.luoyixin.www.orm.annotation.Select;
import com.luoyixin.www.orm.annotation.Update;
import com.luoyixin.www.po.Order;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: OrderDao
 * @create 2021/5/20-20:29
 * @Version: 1.0
 */
@Dao
public interface OrderDao {
    /**
     * 生成订单
     * @param order 订单
     * @return 返回1表示生成成功
     */
    @Insert
    int addOrder(Order order);

    /**
     * 给店长用的
     * 根据店的id查询所有order
     * @param storeId 店的id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status,`user_id` userId,`store_id` storeId from t_order  where store_id = ?")
    List<Order> queryOrdersByStoreId(Integer storeId);

    /**
     * 根据用户id查询 所有list
     * @param userId
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status,`user_id` userId,`store_id` storeId from t_order where user_id = ?")
    List<Order> queryOrdersByUserId(Integer userId);

    /**
     * 更新order
     * @param order
     * @return
     */
    @Update
    Integer updateOrder(Order order);

    /**
     * 查询订单
     * @param orderId
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status,`user_id` userId,`store_id` storeId from t_order where order_id = ?")
    Order queryOrderByOrderId(String orderId);

    /**
     * 返回这周的订单
     * @param userId 用户id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= create_time and user_id = ?")
    List<Order> queryOrderThisWeek(Integer userId);

    /**
     * 查询某个用户这个月的订单
     * @param userId  用户id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m') and user_id =?")
    List<Order> queryOrderThisMonth(Integer userId);


    /**
     * 查询某个用户这个年的订单
     * @param userId 用户id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE YEAR(create_time)=YEAR(NOW()) and user_id = ?")
    List<Order> queryOrderThisYear(Integer userId);

    /**
     * 查询去年某个用户上一年的订单
     * @param userId 用户id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE YEAR(create_time)=YEAR(DATE_SUB(NOW(),INTERVAL 1 YEAR)) and user_Id= ?")
    List<Order> queryOrderLastYear(Integer userId);

    /**
     * 查询去年某个用户当天订单
     * @param userId 用户id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE TO_DAYS(create_time) = TO_DAYS(NOW()) and user_Id= ?")
    List<Order> queryOrderThisDay(Integer userId);

    /**
     * 根据状态查询订单
     * @param storeId
     * @param status
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status,`user_id` userId,`store_id` storeId from t_order where store_id = ? and status = ?")
    List<Order> queryOrdersByStatus(Integer storeId, int status);



    /**
     * 查询去年某个店当天订单
     * @param storeId 店id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE TO_DAYS(create_time) = TO_DAYS(NOW()) and store_id= ?")
    List<Order> queryOrderThisDayByStoreId(Integer storeId);


    /**
     * 返回某个店这周的订单
     * @param storeId 店id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= create_time and store_id = ?")
    List<Order> queryOrderThisWeekByStoreId(Integer storeId);

    /**
     * 查询某个用户这个月的订单
     * @param storeId  店id
     * @return
     */
    @Select("select `id`,`order_id` orderId,`create_time` createTime,`price` price,`status` status," +
            "`user_id` userId,`store_id` storeId from t_order WHERE DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m') and store_id =?")
    List<Order> queryOrderThisMonthByStoreId(Integer storeId);
}
