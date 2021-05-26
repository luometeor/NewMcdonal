package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.UserRole;
import com.luoyixin.www.service.UserRoleService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: UserRoleServiceImplTest
 * @create 2021/5/24-0:04
 * @Version: 1.0
 */
public class UserRoleServiceImplTest {
    private UserRoleService userRoleService = CartItemServiceTest.getBean("userRoleService");

    @Test
    public void addUserRole() {
        userRoleService.addUserRole(new UserRole(null,8,1));
        Assert.assertEquals(1,userRoleService.getRoleId(8));
    }

    @Test
    public void whetherManager() {
        Assert.assertTrue(userRoleService.whetherManager(2));
        Assert.assertFalse(userRoleService.whetherManager(1));

    }

    @Test
    public void getRoleId() {
        Object o =1;
        Assert.assertEquals(o,userRoleService.getRoleId(1));
        o = 2;
        Assert.assertEquals(o,userRoleService.getRoleId(2));

    }
}