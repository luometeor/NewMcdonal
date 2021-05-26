package com.luoyixin.www.controller;

import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.mvc.Result;
import com.luoyixin.www.mvc.innotation.Entity;
import com.luoyixin.www.mvc.innotation.Param;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.po.Destination;
import com.luoyixin.www.po.Information;
import com.luoyixin.www.po.User;
import com.luoyixin.www.service.DestinationService;
import com.luoyixin.www.service.InformationService;
import com.luoyixin.www.service.UserService;
import com.luoyixin.www.vo.Delivery;

import java.util.*;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.controller
 * @ClassName: ClientServlet
 * @create 2021/5/16-9:34
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ajax/pages/client")
public class ClientServlet {
    @Autowired
    private UserService userService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private DestinationService destinationService;

    /**
     * 根据电话号码得到个人的所有信息
     * @param phoneNumber 电话号码
     * @return
     */
    @RequestMapping("/getUser")
    public Result getPersonalInformation(String phoneNumber) {
        Map<String,Object> map = new HashMap<>(200);

        List<Delivery> list = new ArrayList<>();
        User user = userService.login(phoneNumber);
        //用于账户信息
        map.put("user",user);

        List<Information> informationList = informationService.queryInformationByUserId(user.getId());

        Optional.ofNullable(informationList)
                .filter(information -> !information.isEmpty())
                .ifPresent(information -> {
                    information.forEach(info -> {
                        Destination destination = destinationService.getDestinationByDestinationId(info.getDestinationId());
                        list.add(new Delivery(info,destination));
                    });
                    map.put("deliveryList",list);
                });


        return new Result(true,map);
    }

    /**
     * 根据userId删除信息
     * @param informationId 用户id
     * @param phoneNumber 返回跳转页面用到的数据
     * @return
     */
    @RequestMapping("/deleteInformation")
    public Result deleteInformation(@Param("id") Integer informationId,String phoneNumber)  {
         Map<String,Object> map = new HashMap<>(11);

        informationService.deleteInformationByInformationId(informationId);

        map.put("phoneNumber",phoneNumber);

        return new Result(false,map,"/pages/client/personal_information.html");

    }

    /**
     * 增加订餐配置信息
     * @param information 订餐信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/addInformation")
    public Result addUserInformation(@Entity("information.") Information information,@Entity("destination.") Destination destination,String phoneNumber) throws Exception {
        Map<String,Object> map = new HashMap<>(20);

        User user = userService.login(phoneNumber);

        informationService.addInformation(information,destination,user);

        map.put("result",true);

        return new Result(true,map);
    }

    /**
     * 更新用户信息
     * @param information
     * @param destination
     * @param phoneNumber
     * @return
     */
    @RequestMapping("/updateInformation")
    public Result updateInformation(@Entity("information.") Information information,@Entity("destination.") Destination destination,String phoneNumber)  {
        Map<String,Object> map = new HashMap<>(4);

        informationService.updateInformation(information,destination,phoneNumber);

        map.put("result",true);

        return new Result(true,map);

    }

    @RequestMapping("/chooseToDefault")
    public Result chooseToDefault(@Param("informationId") Integer informationId,@Param("userId") Integer userId) {
        List<Information> information = informationService.queryInformationByUserId(userId);

        Optional.ofNullable(information)
                .filter(informationList -> !informationList.isEmpty())
                .ifPresent(informationList -> {
                    informationList.forEach(info ->{
                        //默认的 变成 不是默认的
                        if(info.getIsDefault()) {
                            info.setIsDefault(false);
                            informationService.updateInformation(info);
                        }
                        //id匹配，设置未默认的
                        if(info.getId().compareTo(informationId) == 0) {
                            info.setIsDefault(true);
                            informationService.updateInformation(info);
                        }
                    });
                });


        User user = userService.queryById(userId);

        return new Result(false,"/pages/client/personal_information.html?phoneNumber=" + user.getPhoneNumber());
    }




}
