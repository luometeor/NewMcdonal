package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.User;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: UserService
 * @create 2021/5/5-22:19
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.UserServiceImpl")
public interface UserService {
    /**
     * 注册
     * @param user 要注册的用户
     * @param roleId 用户角色id
     */
    @Transaction
    void register(User user,Integer roleId);

    /**
     * 根据电话号码登入
     * @param phoneNumber 电话号码
     * @return 没有注册过为null
     */
    User login(String phoneNumber);

    /**
     * 是否已经存在电话号码
     * @param phoneNumber 电话号码
     * @return 不已经存在返回true，存在返回false
     */
    Boolean existPhoneNumber(String phoneNumber);


    /**
     * 通过id删除个人信息
     * @param userId 用户id
     */
    void deleteInformationByUserId(Integer userId);

    /**
     * 更新用户信息
     * @param user 已经更新的用户
     */
    void updateInformation(User user);

    /**
     * 根据id查询
     * @param userId 用户id
     * @return 返回查询到的用户，没有为null
     */
    User queryById(Integer userId);

}
