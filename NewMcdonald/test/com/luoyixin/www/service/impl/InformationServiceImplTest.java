package com.luoyixin.www.service.impl;

import com.luoyixin.www.po.Destination;
import com.luoyixin.www.po.Information;
import com.luoyixin.www.po.User;
import com.luoyixin.www.service.DestinationService;
import com.luoyixin.www.service.InformationService;
import com.luoyixin.www.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @author xin
 * @ProjectName: NewMcDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: InformationServiceImplTest
 * @create 2021/5/23-20:05
 * @Version: 1.0
 */
public class InformationServiceImplTest {

    private InformationService informationService = CartItemServiceTest.getBean("informationService");
    private DestinationService destinationService = CartItemServiceTest.getBean("destinationService");
    private UserService userService = CartItemServiceTest.getBean("userService");
    @Test
    public void addInformation() {
        Information information = informationService.queryInformationByInformationId(15);
        information.setUsername("超强的");
        User user = userService.login("13670841997");
        informationService.addInformation(information,destinationService.getDestinationByDestinationId(1),user);
        List<Information> informations = informationService.queryInformationByUserId(user.getId());
        Optional<Information> getInformation = informations.stream().filter(information1 -> information1.getUsername().equals("超强的")).findFirst();
        Object i =1;
        Assert.assertEquals(i,getInformation.get().getDestinationId());
        Assert.assertEquals("超强的",getInformation.get().getUsername());
    }

    @Test
    public void queryInformationByUserId() {
        Assert.assertNotNull(informationService.queryInformationByUserId(1));
    }

    @Test
    public void deleteInformationByInformationId() {
        informationService.deleteInformationByInformationId(16);
        Assert.assertNull(informationService.queryInformationByInformationId(16));
    }

    @Test
    public void updateInformation() {
        Information information = informationService.queryInformationByInformationId(15);
        information.setUsername("差评强");
        Boolean isDefault = information.getIsDefault();
        Destination destination = destinationService.getDestinationByDestinationId(2);
        informationService.updateInformation(information,destination,"13670841997");
        information = informationService.queryInformationByInformationId(15);
        Assert.assertEquals("差评强",information.getUsername());
        Assert.assertEquals(isDefault,information.getIsDefault());
    }

    @Test
    public void queryInformationByInformationId() {
        Assert.assertNull(informationService.queryInformationByInformationId(1));
    }

}