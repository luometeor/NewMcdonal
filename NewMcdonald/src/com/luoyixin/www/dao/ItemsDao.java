package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.Items;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: ItemsDao
 * @create 2021/5/11-15:49
 * @Version: 1.0
 */
@Dao
public interface ItemsDao {
    /**
     * 增加商品
     * @param items
     * @return
     */
    @Insert
    int addItem(Items items);

    /**
     * 根据id删除商品
     * @param itemId 商品id
     * @return
     */
    @Delete("delete from items where id = ?")
    int deleteItem(Integer itemId);

    /**
     * 根据id查询商品
     * @param itemId 商品id
     * @return
     */
    @Select("select `id`,`item_name` itemName,`cool_or_not` cool,`hot_or_not` hot,`img_path` imgPath from items where id = ?")
    Items queryItemByItemId(Integer itemId);

    /**
     * 更新商品
     * @param items
     * @return
     */
    @Update
    int updateItem(Items items);

    /**
     * 获取最大值的id
     * @return
     */
    @Select("select max(id) from items")
    Integer queryMaxId();

}
