package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: UserRole
 * @create 2021/5/9-20:41
 * @Version: 1.0
 */
@Entity(tableName = "user_role")
public class UserRole {
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 用户id
     */
    @Column(columnName = "user_id",alias = "userId")
    private Integer userId;
    /**
     * 角色id
     */
    @Column(columnName = "role_id",alias = "roleId")
    private Integer roleId;

    public UserRole() {
    }

    public UserRole(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
