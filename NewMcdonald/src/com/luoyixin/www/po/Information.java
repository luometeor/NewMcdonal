package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * 送餐信息
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Information
 * @create 2021/5/16-14:17
 * @Version: 1.0
 */
@Entity(tableName = "information")
public class Information {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 用户名
     */
    @Column(columnName = "username",alias = "username")
    private String username;
    /**
     * 性别
     */
    @Column(columnName = "sex",alias = "sex")
    private String sex;
    /**
     * 电话号码
     */
    @Column(columnName = "phone_number",alias = "phoneNumber")
    private String phoneNumber;
    /**
     * 详细地址
     */
    @Column(columnName = "detail_destination",alias = "detailDestination")
    private String detailDestination;
    /**
     * 用户id
     */
    @Column(columnName = "user_id",alias = "userId")
    private Integer userId;
    /**
     * 地址id
     */
    @Column(columnName = "destination_id",alias = "destinationId")
    private Integer destinationId;
    /**
     * 是否为默认地址
     */
    @Column(columnName = "default",alias ="isDefault")
    private Boolean isDefault;

    public Information() {
    }

    public Information(Integer id, String username, String sex, String phoneNumber, String detailDestination, Integer userId, Integer destinationId, Boolean isDefault) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.detailDestination = detailDestination;
        this.userId = userId;
        this.destinationId = destinationId;
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetailDestination() {
        return detailDestination;
    }

    public void setDetailDestination(String detailDestination) {
        this.detailDestination = detailDestination;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", detailDestination='" + detailDestination + '\'' +
                ", userId=" + userId +
                ", destinationId=" + destinationId +
                ", isDefault=" + isDefault +
                '}';
    }
}
