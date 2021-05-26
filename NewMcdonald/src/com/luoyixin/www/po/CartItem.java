package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.math.BigDecimal;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: CartItem
 * @create 2021/5/20-1:08
 * @Version: 1.0
 */
@Entity(tableName = "cart_item")
public class CartItem {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 名称
     */
    @Column(columnName = "item_name",alias = "itemName")
    private String itemName;
    /**
     * 是 多冰 还是...
     */
    @Column(columnName = "cool",alias = "cool")
    private String cool;
    /**
     * 是麻辣 还是...
     */
    @Column(columnName = "hot",alias = "hot")
    private String hot;
    /**
     * 个数
     */
    @Column(columnName = "count",alias = "count")
    private Integer count;
    /**
     * 价格
     */
    @Column(columnName = "price",alias = "price")
    private BigDecimal price;
    /**
     * cart的主键
     */
    @Column(columnName = "cart_id",alias = "cartId")
    private Integer cartId;
    /**
     * 商品id
     */
    @Column(columnName = "item_id",alias = "itemId")
    private Integer itemId;


    /**
     * cartItem的总价格
     */
    @Column(columnName = "total_price",alias = "totalPrice")
    private BigDecimal totalPrice;
    /**
     * 大小份
     */
    @Column(columnName = "size",alias = "size")
    private String size;



    public CartItem() {
    }

    public CartItem(Integer id, String itemName, String cool, String hot, Integer count, BigDecimal price,
                    Integer cartId, Integer itemId, BigDecimal totalPrice,String size) {
        this.id = id;
        this.itemName = itemName;
        this.cool = cool;
        this.hot = hot;
        this.count = count;
        this.price = price;
        this.cartId = cartId;
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.size = size;
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

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", cool='" + cool + '\'' +
                ", hot='" + hot + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", cartId=" + cartId +
                ", itemId=" + itemId +
                ", totalPrice=" + totalPrice +
                ", size='" + size + '\'' +
                '}';
    }
}
