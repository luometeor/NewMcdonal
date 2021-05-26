package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Order
 * @create 2021/5/20-20:08
 * @Version: 1.0
 */
@Entity(tableName = "t_order")
public class Order {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 订单号
     */
    @Column(columnName = "order_id",alias = "orderId")
    private String orderId;
    /**
     * 创建时间
     */
    @Column(columnName = "create_time",alias = "createTime")
    private Date createTime;
    /**
     * 总价格
     */
    @Column(columnName = "price",alias = "price")
    private BigDecimal price;
    /**
     * 0表示未出单，1表示出单
     */
    @Column(columnName = "status",alias = "status")
    private Integer status = 0;
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

    public Order() {
    }

    public Order(Integer id,String orderId, Date createTime, BigDecimal price, Integer status, Integer userId,Integer storeId) {
        this.id = id;
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

     public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                ", storeId=" + storeId +
                '}';
    }
}
