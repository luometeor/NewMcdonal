package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.math.BigDecimal;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Cart
 * @create 2021/5/20-1:12
 * @Version: 1.0
 */
@Entity(tableName = "cart")
public class Cart {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 总个数
     */
    @Column(columnName = "total_count",alias = "totalCount")
    private Integer totalCount;
    /**
     * 总价格
     */
    @Column(columnName = "total_price",alias = "totalPrice")
    private BigDecimal totalPrice;
    /**
     * 用户id
     */
    @Column(columnName = "user_id",alias = "userId")
    private Integer userId;
    /**
     * 店的id
     */
    @Column(columnName = "store_id",alias = "storeId")
    private Integer storeId;

    public Cart() {
    }

    public Cart(Integer id, Integer totalCount, BigDecimal totalPrice,Integer userId,Integer storeId) {
        this.id = id;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {


        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalCount=" + totalCount +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", storeId=" + storeId +
                '}';
    }
}
