package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.UserDao;
import com.luoyixin.www.dao.UserRoleDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.User;
import com.luoyixin.www.po.UserRole;
import com.luoyixin.www.service.UserService;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: UserServiceImpl
 * @create 2021/5/5-22:20
 * @Version: 1.0
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public void register(User user,Integer roleId) {
        userDao.addUser(user);
        //更新到本地上
        Integer userId = userDao.queryUserByPhoneNumber(user.getPhoneNumber()).getId();
        userRoleDao.addUserRole(new UserRole(null,userId,roleId));
    }

    @Override
    public User login(String phoneNumber) {
        return userDao.queryUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existPhoneNumber(String phoneNumber) {
        User user = userDao.queryUserByPhoneNumber(phoneNumber);
        if (user == null ) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteInformationByUserId(Integer userId) {
        userDao.deleteByUserId(userId);
    }

    @Override
    public void updateInformation(User user) {
        userDao.update(user);
    }

    @Override
    public User queryById(Integer userId) {
        return userDao.queryUserByUserId(userId);
    }


}
