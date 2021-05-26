package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.math.BigDecimal;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: PriceSize
 * @create 2021/5/16-14:46
 * @Version: 1.0
 */
@Entity(tableName = "price_size")
public class PriceSize {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 尺寸
     */
    @Column(columnName = "size",alias = "size")
    private String size;
    /**
     * 价格
     */
    @Column(columnName = "price",alias = "price")
    private BigDecimal price;
    /**
     * 商品id
     */
    @Column(columnName = "item_id",alias = "itemId")
    private Integer itemId;

    public PriceSize() {
    }


    public PriceSize(Integer id, String size, BigDecimal price, Integer itemId) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "PriceSize{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", itemId=" + itemId +
                '}';
    }
}
