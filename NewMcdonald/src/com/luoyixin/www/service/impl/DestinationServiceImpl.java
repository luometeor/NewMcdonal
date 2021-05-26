package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.DestinationDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.Destination;
import com.luoyixin.www.service.DestinationService;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: DestinationServiceImpl
 * @create 2021/5/16-19:40
 * @Version: 1.0
 */
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private DestinationDao destinationDao;
    @Override
    public Destination getDestinationByDestinationId(Integer destinationId) {
        return destinationDao.getDestinationByDestinationId(destinationId);
    }

    @Override
    public Integer getDestinationId(Destination destination) {
        if(destination != null) {
            String province = destination.getProvince();
            String city = destination.getCity();
            String stress = destination.getStress();
            if (province != null && city != null && stress != null) {
                return destinationDao.getDestinationIdByDestinationId(province, city, stress);
            }
        }
        return null;
    }
}
