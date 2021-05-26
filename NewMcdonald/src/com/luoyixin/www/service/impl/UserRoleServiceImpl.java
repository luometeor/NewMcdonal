package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.UserRoleDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.UserRole;
import com.luoyixin.www.service.UserRoleService;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: UserRoleServiceImpl
 * @create 2021/5/9-20:52
 * @Version: 1.0
 */
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public void addUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }

    @Override
    public Boolean whetherManager(Integer userId) {
        UserRole userRole = userRoleDao.queryUserRole(userId);
        if(userRole == null || userRole.getRoleId() == 1) {
            return false;
        }
        return true;
    }

    @Override
    public int getRoleId(Integer userId) {
        return userRoleDao.queryUserRole(userId).getRoleId();
    }
}
