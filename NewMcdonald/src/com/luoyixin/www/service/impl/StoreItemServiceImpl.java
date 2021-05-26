package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.StoreItemDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.StoreItem;
import com.luoyixin.www.service.StoreItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: StoreItemServiceImpl
 * @create 2021/5/15-0:07
 * @Version: 1.0
 */
public class StoreItemServiceImpl implements StoreItemService {
    @Autowired
    private StoreItemDao storeItemDao;

    @Override
    public int addStoreItem(StoreItem storeItem) {
        return storeItemDao.addStoreItem(storeItem);
    }



    @Override
    public List<Integer> queryStoreItemByStoreId(Integer storeId) {
        List<StoreItem> storeItems = storeItemDao.queryStoreItemByStoreId(storeId);
        List<Integer> list  = new ArrayList<>();

        Optional.ofNullable(storeItems)
                .filter(storeItem -> !storeItem.isEmpty())
                .ifPresent(storeItem -> {
                    storeItem.forEach(items -> {
                        list.add(items.getItemId());
                    });
                });

        return list;
    }


}
