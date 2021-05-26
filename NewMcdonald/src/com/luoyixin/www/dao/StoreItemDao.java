package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.Dao;
import com.luoyixin.www.orm.annotation.Delete;
import com.luoyixin.www.orm.annotation.Insert;
import com.luoyixin.www.orm.annotation.Select;
import com.luoyixin.www.po.StoreItem;

import java.util.List;

/**
 * 店---商品 中间表
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: StoreItemDao
 * @create 2021/5/15-0:01
 * @Version: 1.0
 */
@Dao
public interface StoreItemDao {
    /**
     * 增加关系
     * @param storeItem 要增加的关系
     * @return 返回1表示增加成功，反之失败
     */
    @Insert
    int addStoreItem(StoreItem storeItem);

    /**
     * 根据主键id删除关系
     * @param id 主键id
     * @return 返回1表示删除成功，反之失败
     */
    @Delete("delete from store_item where id = ?")
    int deleteStoreItem(Integer id);

    /**
     * 根据店的id查询到商品的所有id
     * @param storeId 店d
     * @return 返回集合，没有的话为null
     */
    @Select("select `id`,`items_id` itemId ,`store_id` storeId from store_item where store_id = ?")
    List<StoreItem> queryStoreItemByStoreId(Integer storeId);

    /**
     * 根据商品id删除 所有店与商品关系
     * @param itemId 商品id
     * @return
     */
    @Delete("delete from store_item where items_id = ?")
    Integer deleteStoreItemByItemId(Integer itemId);
}
