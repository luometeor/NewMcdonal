package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.Dao;
import com.luoyixin.www.orm.annotation.Insert;
import com.luoyixin.www.orm.annotation.Select;
import com.luoyixin.www.po.UserRole;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: UserRole
 * @create 2021/5/9-20:40
 * @Version: 1.0
 */
@Dao
public interface UserRoleDao {

    /**
     * 给用户定角色
     * @param userRole
     * @return
     */
    @Insert
    int addUserRole(UserRole userRole);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    @Select("select id,role_id roleId,user_id userId from user_role where user_id = ?")
    UserRole queryUserRole(Integer userId);
}
