package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.PriceSizeDao;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.po.PriceSize;
import com.luoyixin.www.service.PriceSizeService;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: PriceSizeServiceImpl
 * @create 2021/5/17-16:24
 * @Version: 1.0
 */
public class PriceSizeServiceImpl implements PriceSizeService {
    @Autowired
    private PriceSizeDao priceSizeDao;

    @Override
    public List<PriceSize> getPriceSizeByItemId(Integer itemId) {
        return priceSizeDao.queryPriceSizeByItemId(itemId);
    }
}
