package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.math.BigDecimal;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: OrderItem
 * @create 2021/5/20-20:22
 * @Version: 1.0
 */
@Entity(tableName = "t_order_item")
public class OrderItem {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 商品名称
     */
    @Column(columnName = "item_name",alias = "itemName")
    private String itemName;
    /**
     * 个数
     */
    @Column(columnName = "count",alias = "count")
    private Integer count;
    /**
     * 单价
     */
    @Column(columnName = "price",alias = "price")
    private BigDecimal price;
    /**
     * 总价
     */
    @Column(columnName = "total_price",alias = "totalPrice")
    private BigDecimal totalPrice;
    /**
     * 订单号
     */
    @Column(columnName = "order_id",alias = "orderId")
    private String orderId;
    /**
     * 是否加冰
     */
    @Column(columnName = "cool",alias = "cool")
    private String cool;
    /**
     * 是否加辣
     */
    @Column(columnName = "hot",alias = "hot")
    private String hot;
    /**
     * 大小
     */
    @Column(columnName = "size",alias = "size")
    private String size;
    /**
     * 店名
     */
    @Column(columnName = "store_name",alias = "storeName")
    private String storeName;

    public OrderItem() {
    }

    public OrderItem(Integer id, String itemName, Integer count, BigDecimal price, BigDecimal totalPrice, String orderId, String cool, String hot, String size,String storeName) {
        this.id = id;
        this.itemName = itemName;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.cool = cool;
        this.hot = hot;
        this.size = size;
        this.storeName = storeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCool() {
        return cool;
    }

    public void setCool(String cool) {
        this.cool = cool;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", orderId='" + orderId + '\'' +
                ", cool='" + cool + '\'' +
                ", hot='" + hot + '\'' +
                ", size='" + size + '\'' +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
