package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.UserRole;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: UserRoleService
 * @create 2021/5/9-20:46
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.UserRoleServiceImpl")
public interface UserRoleService {

    /**
     * 给用户定角色
     * @param userRole
     */
    @Transaction
    void addUserRole(UserRole userRole);

    /**
     * 是否是店长
     * @param userId 用户id
     * @return true，是店长，反之是顾客
     */
    Boolean whetherManager(Integer userId);

    /**
     * 根据userId获取roleId
     * @param userId 用户id
     * @return 返回1->顾客或者2->店长
     */
    int getRoleId(Integer userId);
}
