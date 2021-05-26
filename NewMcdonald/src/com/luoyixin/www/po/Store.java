package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Store
 * @create 2021/5/13-0:28
 * @Version: 1.0
 */
@Entity(tableName = "store")
public class Store {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 店名
     */
    @Column(columnName = "store_name",alias = "storeName")
    private String storeName;
    /**
     * 用户id，开店的那个人的id
     */
    @Column(columnName = "user_id",alias = "userId")
    private Integer userId;
    /**
     * 描述
     */
    @Column(columnName = "description",alias = "description")
    private String description;
    /**
     * 所在城市
     */
    @Column(columnName = "city",alias = "city")
    private String city;

    public Store() {
    }

    public Store(Integer id, String storeName, Integer userId, String description, String city) {
        this.id = id;
        this.storeName = storeName;
        this.userId = userId;
        this.description = description;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
