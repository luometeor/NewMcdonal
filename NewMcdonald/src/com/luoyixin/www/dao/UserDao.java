package com.luoyixin.www.dao;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.po.User;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.dao
 * @ClassName: UserDao
 * @create 2021/5/5-22:18
 * @Version: 1.0
 */
@Dao
public interface UserDao {
    /**
     *  增加用户
     * @param user
     * @return 返回1为成功，返回0为添加失败
     */
    @Insert
    int addUser(User user);


    /**
     * 根据电话号码查询用户
     * @param phoneNumber 电话号码
     * @return
     */
    @Select("select `id`,`phone_number` phoneNumber,`email` email from t_user where phone_number = ?")
    User queryUserByPhoneNumber(String phoneNumber);


    /**
     * 根据id删除
     * @param userId 用户id
     * @return 返回1为成功，返回0为删除失败
     */
    @Delete("delete from t_user where id = ?")
    int deleteByUserId(Integer userId);

    /**
     * 更新
     * @param user 用更新的user
     * @return 返回1为成功，返回0为更新失败
     */
    @Update
    int update(User user);

    /**
     * 根据id查询用户
     * @param userId 用户id
     * @return 返回查询到的用户，没有为null
     */
    @Select("select `id`,`phone_number` phoneNumber,`email` from t_user where id = ?")
    User queryUserByUserId(Integer userId);

}
