package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.po.StoreItem;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: StoreItemService
 * @create 2021/5/15-0:06
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.StoreItemServiceImpl")
public interface StoreItemService {
    /**
     * 增加关系
     * @param storeItem 要增加的关系
     * @return 返回1表示增加成功，反之失败
     */
    int addStoreItem(StoreItem storeItem);



    /**
     * 根据店id查询
     * @param storeId 店id
     * @return 返回集合，没有的话为null
     */
    List<Integer> queryStoreItemByStoreId(Integer storeId);

}
