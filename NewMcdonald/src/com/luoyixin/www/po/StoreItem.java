package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * 中间表
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: StoreItem
 * @create 2021/5/14-23:59
 * @Version: 1.0
 */
@Entity(tableName = "store_item")
public class StoreItem {
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 商品id
     */
    @Column(columnName = "items_id",alias = "itemId")
    private Integer itemId;
    /**
     * 店的id
     */
    @Column(columnName = "store_id",alias = "storeId")
    private Integer storeId;

    public StoreItem() {
    }

    public StoreItem(Integer id, Integer itemId, Integer storeId) {
        this.id = id;
        this.itemId = itemId;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", storeId=" + storeId +
                '}';
    }
}
