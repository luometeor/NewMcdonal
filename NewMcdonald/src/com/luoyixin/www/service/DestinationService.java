package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.po.Destination;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: DestinationService
 * @create 2021/5/16-19:36
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.DestinationServiceImpl")
public interface DestinationService {
    /**
     * 获取 具体的位置（省，城市，街区）
     * @param destinationId 位置的id
     * @return 返回位置（省，城市，街区）
     */
    Destination getDestinationByDestinationId(Integer destinationId);

    /**
     * 获取 destination的id
     * @param destination 没有id的destination的id
     * @return 若没有查询到，返回null
     */
    Integer getDestinationId(Destination destination);

}
