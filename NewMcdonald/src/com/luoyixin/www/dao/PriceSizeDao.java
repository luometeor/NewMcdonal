package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.PriceSize;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: PriceSizeDao
 * @create 2021/5/17-16:24
 * @Version: 1.0
 */
@Dao
public interface PriceSizeDao {
    /**
     * 增加商品尺寸表
     * @param priceSize
     */
    @Insert
     void addPriceSize(PriceSize priceSize);

    /**
     * 更新
     * @param priceSize
     */
    @Update
    void updatePriceSize(PriceSize priceSize);

    /**
     * 根据商品id查询
     * @param itemId 商品id
     * @return
     */
    @Select("select `id`,`size`,`price`,`item_id` itemId from price_size where item_id = ?")
    List<PriceSize> queryPriceSizeByItemId(Integer itemId);

    /**
     * 根据主键id删除
     * @param priceSizeId
     */
    @Delete("delete from price_size where id = ?")
    void deletePriceSizeById(Integer priceSizeId);
}
