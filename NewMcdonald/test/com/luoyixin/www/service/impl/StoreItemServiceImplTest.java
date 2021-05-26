package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.StoreItem;
import com.luoyixin.www.service.StoreItemService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: StoreItemServiceImplTest
 * @create 2021/5/24-7:17
 * @Version: 1.0
 */
public class StoreItemServiceImplTest {
    private StoreItemService storeItemService = CartItemServiceTest.getBean("storeItemService");

    @Test
    public void queryStoreItemByStoreId() {
        List<Integer> list = storeItemService.queryStoreItemByStoreId(13);
        Assert.assertEquals(2,list.size());
    }

    @Test
    public void addStoreItem() {
        List<Integer> list = storeItemService.queryStoreItemByStoreId(13);
        Assert.assertEquals(3,list.size());
        storeItemService.addStoreItem(new StoreItem(null,list.get(0),13));
        list = storeItemService.queryStoreItemByStoreId(13);
        Assert.assertEquals(4,list.size());

    }


}