package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.Destination;
import com.luoyixin.www.service.DestinationService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: DestinationServiceImplTest
 * @create 2021/5/23-20:50
 * @Version: 1.0
 */
public class DestinationServiceImplTest {

    private DestinationService destinationService = CartItemServiceTest.getBean("destinationService");
    @Test
    public void getDestinationByDestinationId() {
        Destination destination = destinationService.getDestinationByDestinationId(1);
        Assert.assertEquals("广东省",destination.getProvince());
        Assert.assertEquals("梅州市",destination.getCity());
        Assert.assertNotEquals("丰顺县",destination.getProvince());

    }

    @Test
    public void getDestinationId() {
        Object i = 2;
        assertEquals(i,destinationService.getDestinationId(new Destination(null,"广东省","梅州市","丰顺县")));
        assertNull(destinationService.getDestinationId(null));
    }
}