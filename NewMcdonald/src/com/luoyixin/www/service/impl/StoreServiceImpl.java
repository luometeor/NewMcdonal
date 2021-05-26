package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.StoreDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.Store;
import com.luoyixin.www.service.StoreService;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: StoreServiceImpl
 * @create 2021/5/13-9:08
 * @Version: 1.0
 */
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;

    @Override
    public List<Store> queryStores() {
        return storeDao.queryList();
    }

    @Override
    public void deleteStoreByStoreId(Integer storeId) {
        storeDao.deleteStoreByStoreId(storeId);
    }

    @Override
    public int addStore(Store store) {
        return storeDao.addStore(store);
    }

    @Override
    public void updateStore(Store store) {
        storeDao.updateStore(store);
    }

    @Override
    public List<Store> queryStoresByUserId(Integer userId) {
        return storeDao.queryListByUserId(userId);
    }

    @Override
    public Store queryStoresById(Integer storeId) {
        return storeDao.queryStoreById(storeId);
    }
}
