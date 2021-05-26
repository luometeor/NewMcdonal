package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.po.PriceSize;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: PriceSizeService
 * @create 2021/5/17-16:16
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.PriceSizeServiceImpl")
public interface  PriceSizeService {

    /**
     * 根据商品id查找
     * @param itemId
     * @return 没有的话返回空集合
     */
    List<PriceSize> getPriceSizeByItemId(Integer itemId);


}
