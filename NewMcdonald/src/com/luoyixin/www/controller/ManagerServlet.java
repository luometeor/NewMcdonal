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
import com.luoyixin.www.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: ManagerServlet
 * @create 2021/5/17-18:27
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/manager")
public class ManagerServlet {
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;

    /**
     * 修改店的信息
     * @param store
     * @return
     */
    @RequestMapping("/updateStore")
    public Result updateStore(@Entity("store.") Store store) {
        Map<String,Object> map = new HashMap<>(30);
        storeService.updateStore(store);
        User user = userService.queryById(store.getUserId());
        map.put("phoneNumber",user.getPhoneNumber());
        map.put("result",true);
        return  new Result(true,map);
    }

    /**
     * 添加店
     * @param store
     * @return
     */
    public Result addStore(@Entity("store.") Store store) {
        Map<String,Object> map = new HashMap<>(4+11);

        storeService.addStore(store);

        User user = userService.queryById(store.getUserId());

        map.put("result",true);
        map.put("phoneNumber",user.getPhoneNumber());
        return new Result(true,map);
    }


    /**
     * 进入我的店
     * @param userId
     * @return
     */
    @RequestMapping("/gotoMyStore")
    public Result gotoMyStore(@Param("userId") Integer userId) {
        Map<String,Object> map = new HashMap<>(50);

        List<Store> stores = storeService.queryStoresByUserId(userId);

        if(stores == null || stores.isEmpty()) {
            map.put("result",false);
        } else {
            map.put("stores",stores);
            map.put("result",true);
        }

        return new Result(true,map);
    }

    /**
     * 删除店铺
     * @param storeId
     * @return
     */
    @RequestMapping("/deleteStore")
    public Result deleteStore(@Param("storeId") Integer storeId) {
        Store store = storeService.queryStoresById(storeId);

        storeService.deleteStoreByStoreId(storeId);

        User user = userService.queryById(store.getUserId());

        return new Result(false,"/pages/manager/store.html?userId="+user.getId()+"&phoneNumber="+user.getPhoneNumber());
    }




}
