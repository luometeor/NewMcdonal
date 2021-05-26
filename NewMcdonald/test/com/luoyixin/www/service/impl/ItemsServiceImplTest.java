package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.Items;
import com.luoyixin.www.service.ItemsService;
import com.luoyixin.www.service.PriceSizeService;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: ItemsServiceImplTest
 * @create 2021/5/23-20:54
 * @Version: 1.0
 */
public class ItemsServiceImplTest {
    private ItemsService itemsService = CartItemServiceTest.getBean("itemsService");
    private PriceSizeService priceSizeService = CartItemServiceTest.getBean("priceSizeService");

    @Test
    public void queryMaxId() {
        Object i = 75;
        Assert.assertEquals(i,itemsService.queryMaxId());
    }

    @Test
    public void addItem() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itemName","好吃");
        hashMap.put("size","");
        hashMap.put("cool","true");
        hashMap.put("hot","false");
        hashMap.put("price","123.12");
        itemsService.addItem(hashMap,8);
        Integer maxId = itemsService.queryMaxId();
        Items items = itemsService.queryItemByItemId(maxId);
        Assert.assertTrue(items.getCool());
        Assert.assertFalse(items.getHot());
        Assert.assertEquals("好吃",items.getItemName());
        hashMap.remove("price");
        hashMap.put("bigPrice","12.12");
        hashMap.put("midPrice","5.12");
        hashMap.put("smallPrice","2.12");
        itemsService.addItem(hashMap,8);
        Integer maxId1 = itemsService.queryMaxId();
        Items items1 = itemsService.queryItemByItemId(maxId1);
        Object o = 3;
       Assert.assertEquals( o,priceSizeService.getPriceSizeByItemId(items1.getId()).size());

    }

    @Test
    public void queryItemByItemId() {
        Items items = itemsService.queryItemByItemId(76);
        Assert.assertNull(items);
        Assert.assertNotNull(itemsService.queryItemByItemId(39));
    }



    @Test
    public void updateItem() {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            //是套餐
            hashMap.put("itemName","好吃");
            hashMap.put("size","");
            hashMap.put("cool","true");
            hashMap.put("hot","false");
            hashMap.put("price","123.12");
            itemsService.updateItem(hashMap,69);
            Items items = itemsService.queryItemByItemId(69);
            Assert.assertFalse((items.getHot()));
            Assert.assertEquals("好吃",items.getItemName());
            Object j = 1;
            Assert.assertEquals(j,priceSizeService.getPriceSizeByItemId(69).size());
            if(i == 0) {
                //不是套餐
                hashMap.remove("price");
                hashMap.put("bigPrice","12.12");
                hashMap.put("midPrice","5.12");
                hashMap.put("smallPrice","2.12");
                itemsService.updateItem(hashMap,69);
                j = 3;
                Assert.assertEquals(j,priceSizeService.getPriceSizeByItemId(69).size());
                hashMap.remove("bigPrice");
                hashMap.remove("midPrice");
                hashMap.remove("smallPrice");
            } else {
                //是套餐
                hashMap.put("itemName","6666");
                Object o = "6666";
                itemsService.updateItem(hashMap,69);
                Assert.assertEquals(o,itemsService.queryItemByItemId(69).getItemName());
            }
            //不是套餐变成不是套餐
            Map<String,Object> map = new HashMap<>();
            map.put("bigPrice","12.12");
            map.put("midPrice","5.12");
            map.put("smallPrice","2.12");
            map.put("itemName","好吃649");
            map.put("size","");
            map.put("cool","false");
            map.put("hot","true");
            itemsService.updateItem(map,75);
            Items items1 = itemsService.queryItemByItemId(75);
            Assert.assertTrue(items1.getHot());
            Assert.assertEquals("好吃649",items1.getItemName());

        }

    }

    @Test
    public void deleteItemByItemId() {
        itemsService.deleteItemByItemId(74);
        Assert.assertTrue(priceSizeService.getPriceSizeByItemId(74).isEmpty());
    }
}