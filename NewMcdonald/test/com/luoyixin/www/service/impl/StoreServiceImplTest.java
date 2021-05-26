package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.Store;
import com.luoyixin.www.service.StoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: StoreServiceImplTest
 * @create 2021/5/24-0:13
 * @Version: 1.0
 */
public class StoreServiceImplTest {
    private StoreService storeService = CartItemServiceTest.getBean("storeService");
    @Test
    public void queryStores() {
        Assert.assertNotNull(storeService.queryStores());
    }

    @Test
    public void deleteStoreByStoreId() {
        storeService.deleteStoreByStoreId(14);
        Assert.assertNull(storeService.queryStoresById(14));
    }

    @Test
    public void addStore() {
        Store store = storeService.queryStoresById(12);
        store.setStoreName("hhhh");
        storeService.addStore(store);
        Store r = storeService.queryStoresById(14);
        Assert.assertEquals("hhhh",r.getStoreName());
    }

    @Test
    public void updateStore() {
        Store store = storeService.queryStoresById(12);
        store.setStoreName("aaa");
        store.setDescription("hhhh");
        storeService.updateStore(store);
        Assert.assertEquals("aaa",storeService.queryStoresById(12).getStoreName());
        Assert.assertEquals("hhhh",storeService.queryStoresById(12).getDescription());
    }

    @Test
    public void queryStoresByUserId() {
        Assert.assertEquals(3,storeService.queryStoresByUserId(2).size());
    }

    @Test
    public void queryStoresById() {
        Assert.assertNotNull(storeService.queryStoresById(8));
       Assert.assertNull(storeService.queryStoresById(20));
    }
}