package com.luoyixin.www.controller;

import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.mvc.Result;
import com.luoyixin.www.mvc.innotation.Entity;
import com.luoyixin.www.mvc.innotation.Param;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.po.Store;
import com.luoyixin.www.po.User;
import com.luoyixin.www.service.StoreService;
import com.luoyixin.www.service.UserRoleService;
import com.luoyixin.www.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: UserServlet
 * @create 2021/5/5-22:43
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/user")
public class UserServlet  {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService ;
    @Autowired
    private StoreService storeService;

    /**
     * 登入
     * @param phoneNumber
     * @return
     */
    @RequestMapping("/login")
    public Result login(String phoneNumber) {
        Map<String,Object> map = new HashMap<>(5);
        User loginUser = userService.login(phoneNumber);

        if(loginUser == null) {
            map.put("result",false);
        } else {
            map.put("result",true);
        }
        return new Result(true,map);
    }

    /**
     * 注册
     * @param user 用户
     * @param roleId 角色id
     * @return
     */
    @RequestMapping("/register")
    public Result register(@Entity("user.") User user, @Param("roleId") Integer roleId)  {
        Map<String,Object> map = new HashMap<>(5);
        if (userService.existPhoneNumber(user.getPhoneNumber())) {

            userService.register(user,roleId);

            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return new Result(true,map);
    }


    /**
     * 会到主页面
     * @param phoneNumber 根据电话号码回到主页面
     * @param storeId 一定要是自己开的店-->一定是一个店长
     * @return
     */
    public Result gotoLoginSuccess(String phoneNumber,Integer userId,Integer storeId) {
        Map<String,Object> map = new HashMap<>(20);
        User user = null;

        if(phoneNumber != null) {
            user = userService.login(phoneNumber);
        } else if( storeId != null) {
            Store store = storeService.queryStoresById(storeId);
            user = userService.queryById(store.getUserId());
        } else if(userId != null) {
            user = userService.queryById(userId);
        }

        int roleId = userRoleService.getRoleId(user.getId());

        map.put("roleId",roleId);
        map.put("userId",user.getId());
        map.put("phoneNumber",user.getPhoneNumber());

        return new Result(false,map,"/pages/user/login_success.html");

    }

}
