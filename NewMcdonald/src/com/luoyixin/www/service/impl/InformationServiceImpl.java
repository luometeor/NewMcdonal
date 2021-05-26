package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.DestinationDao;
import com.luoyixin.www.dao.InformationDao;
import com.luoyixin.www.dao.UserDao;
import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.exception.InfomationException;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.Destination;
import com.luoyixin.www.po.Information;
import com.luoyixin.www.po.User;
import com.luoyixin.www.service.InformationService;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: InformationServiceImpl
 * @create 2021/5/16-17:41
 * @Version: 1.0
 */
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;
    @Autowired
    private DestinationDao destinationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void addInformation(Information information, Destination destination,User user) {
        //已经存在的信息
        List<Information> existInformation = informationDao.queryInformationByUserId(user.getId());

        if(existInformation == null || existInformation.isEmpty() ) {
            //设置为默认地址
            information.setIsDefault(true);
        } else {
            information.setIsDefault(false);
        }
        //完善信息
        information.setUserId(user.getId());

        Integer destinationId = getDestinationId(destination);

        information.setDestinationId(destinationId);
        //增加信息
        informationDao.addInformation(information);
    }

    @Override
    public List<Information> queryInformationByUserId(Integer userId) {
        return informationDao.queryInformationByUserId(userId);
    }


    @Override
    public void deleteInformationByInformationId(Integer informationId) {
        informationDao.deleteInformationByInformationId(informationId);
    }

    @Override
    public void updateInformation(Information information,Destination destination,String phoneNumber) {
        //获取以前的信息
        Information prevInformation = informationDao.queryInformationByInformationId(information.getId());

        Integer destinationId = getDestinationId(destination);

        User user = userDao.queryUserByPhoneNumber(phoneNumber);
        //完善信息
        information.setUserId(user.getId());

        information.setDestinationId(destinationId);
        if(prevInformation.getIsDefault()) {
            information.setIsDefault(true);
        } else {
            information.setIsDefault(false);
        }
        informationDao.updateInformation(information);
    }

    @Override
    public Information queryInformationByInformationId(Integer informationId) {
        return informationDao.queryInformationByInformationId(informationId);
    }

    @Override
    public void updateInformation(Information information) {
        informationDao.updateInformation(information);
    }

    /**
     * 返回destinationId
     * @param destination
     * @return
     */
    private Integer getDestinationId(Destination destination) {

        String province = destination.getProvince();
        String city = destination.getCity();
        String stress = destination.getStress();
        if(province == null || city == null || stress == null) {
            throw new InfomationException(Constant.DESTINATION_WITHOUT_DATA);
        }
        return destinationDao.getDestinationIdByDestinationId(province,city,stress);
    }
}
