package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.Destination;
import com.luoyixin.www.po.Information;
import com.luoyixin.www.po.User;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: InformationService
 * @create 2021/5/16-17:25
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.InformationServiceImpl")
public interface InformationService {
    /**
     * 增加配餐信息
     * @param information 新加信息
     * @param destination 新加的地址
     * @param user 那个用户要加信息
     */
    @Transaction
    void addInformation(Information information, Destination destination, User user);

    /**
     * 查询配餐信息根据用户id
     * @param userId 用户id
     * @return 返回配餐信息，没有的话为null
     */
    List<Information> queryInformationByUserId(Integer userId);

    /**
     * 删除配餐信息
     * @param informationId 配餐信息id
     */
    void deleteInformationByInformationId(Integer informationId);

    /**
     * 更新配餐信息
     * @param information 要更新的信息
     * @param destination 要更新的地址
     * @param phoneNumber 电话号码-->谁在更新
     */
    void updateInformation(Information information,Destination destination,String phoneNumber);

    /**
     * 根据主键获取信息
     * @param informationId
     * @return
     */
    Information queryInformationByInformationId(Integer informationId);

    /**
     * 设置默认地址
     * @param information
     */
    void updateInformation(Information information);
}
