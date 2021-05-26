package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.po.Store;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: StoreService
 * @create 2021/5/13-9:06
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.StoreServiceImpl")
public interface StoreService {
    /**
     * 查询所有的店
     *
     * @return 返回list，没有查询到null
     */
    List<Store> queryStores();

    /**
     * 根据店的id删除店
     * @param storeId 店的id
     */
    void deleteStoreByStoreId(Integer storeId);

    /**
     * 增加店
     * @param store 待增加的店
     * @return 返回1表示添加成功
     */
    int addStore(Store store);

    /**
     * 更新店
     * @param store 待更新的店
     */
    void updateStore(Store store);

    /**
     * 根据userId查询到所有的店
     * @param userId 用户id
     * @return
     */
    List<Store> queryStoresByUserId(Integer userId);

    /**
     * 根据主键id查询店
     * @param storeId 主键id
     * @return 返回店，没有的话为null
     */
    Store queryStoresById(Integer storeId);
}
