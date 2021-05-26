package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.User;
import com.luoyixin.www.service.UserService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: UserServiceImplTest
 * @create 2021/5/23-22:09
 * @Version: 1.0
 */
public class UserServiceImplTest {
    private UserService userService = CartItemServiceTest.getBean("userService");
    @Test
    public void register() {
        userService.register(new User(null,"11111111111","asd@qq.com"),1);
        Assert.assertNotNull(userService.login("11111111111"));
    }

    @Test
    public void login() {
       Assert.assertNotNull(userService.login("11111111111"));
       Assert.assertNull(userService.login("123156"));
    }

    @Test
    public void existPhoneNumber() {
        Assert.assertFalse(userService.existPhoneNumber("11111111111"));
        Assert.assertTrue(userService.existPhoneNumber("133156"));
    }

    @Test
    public void deleteInformationByUserId() {
        userService.deleteInformationByUserId(5);
        Assert.assertNull(userService.queryById(5));
    }

    @Test
    public void updateInformation() {
        User user = userService.queryById(6);
        user.setEmail("wwwwww");
        userService.updateInformation(user);
        user =  userService.queryById(6);
        Assert.assertEquals("wwwwww",user.getEmail());
    }

    @Test
    public void queryById() {
        Assert.assertNotNull(userService.queryById(6));
        Assert.assertNull(userService.queryById(5));
    }
}