package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: User
 * @create 2021/5/1-18:43
 * @Version: 1.0
 */
@Entity(tableName = "t_user")
public class User {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 电话号码
     */

    @Column(columnName = "phone_number",alias = "phoneNumber")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @Column(columnName = "email",alias = "email")
    private String email;

    public User() {
    }

    public User(Integer id, String phoneNumber, String email) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
