package com.luoyixin.www.util;

import com.luoyixin.www.po.CartItem;
import com.luoyixin.www.po.Items;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.util
 * @ClassName: WebUtilsTest
 * @create 2021/5/24-8:37
 * @Version: 1.0
 */
public class WebUtilsTest {

    @Test
    public void copyParamToBean() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("itemName","666");
        map.put("cool","冰");
        map.put("price","12.12");
        map.put("?","?");
        map.put("count","10");
        Object o = 10;
        CartItem cartItem = WebUtils.copyParamToBean(map, new CartItem());
        Assert.assertEquals("666",cartItem.getItemName());
        Assert.assertEquals(o,cartItem.getCount());
        Map<String,Object> map2 = new HashMap<>();
        map2.put("itemName","666");
        map2.put("cool","true");
        Items items = WebUtils.copyParamToBean(map2, new Items());
        Assert.assertTrue(items.getCool());
    }

    @Test
    public void getParams() {

    }

    @Test
    public void parseInt() {
        Object o = 1;
       Assert.assertEquals(o,WebUtils.parseInt("1", 0));
       o = 0;
        Assert.assertEquals(o,WebUtils.parseInt("asd", 0));

    }
}