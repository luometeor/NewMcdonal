package com.luoyixin.www.service.impl;

import com.luoyixin.www.dao.ItemsDao;
import com.luoyixin.www.dao.PriceSizeDao;
import com.luoyixin.www.dao.StoreItemDao;
import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.exception.ItemException;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.ioc.manager.TransactionStatus;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.po.Items;
import com.luoyixin.www.po.PriceSize;
import com.luoyixin.www.po.StoreItem;
import com.luoyixin.www.service.ItemsService;
import com.luoyixin.www.util.WebUtils;
import com.luoyixin.www.vo.Page;
import com.luoyixin.www.vo.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service.impl
 * @ClassName: ItemsServiceImpl
 * @create 2021/5/11-16:07
 * @Version: 1.0
 */
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsDao itemsDao;
    @Autowired
    private StoreItemDao storeItemDao;
    @Autowired
    private PriceSizeDao priceSizeDao;

    private Logger logger = JdkLogger.getLogger();

    @Override
    public void addItem(Map<String,Object> param, Integer storeId) {

        Items items = new Items();

        try {
            items = WebUtils.copyParamToBean(param,items);
        } catch (Exception e) {
            //告诉事务回滚
            TransactionStatus.setStatus(-1);
            logger.warning(String.format("错误为{%s},参数为{%s}，该商品为{%s}",Constant.PARAM_NO_MATCH,param.toString(),items.toString()));
        }
        itemsDao.addItem(items);
        //主键的最大id
        Integer maxId = itemsDao.queryMaxId();

        storeItemDao.addStoreItem(new StoreItem(null,maxId,storeId));

        //增加商品属性，不是套餐
        if(param.get(Constant.PRICE) == null) {
            addPriceSize(param,maxId);
        } else {
            priceSizeDao.addPriceSize(new PriceSize(null,null,new BigDecimal((String) param.get(Constant.PRICE)),maxId));
        }

    }

    @Override
    public Items queryItemByItemId(Integer itemId) {
        return itemsDao.queryItemByItemId(itemId);
    }

    @Override
    public Integer queryMaxId() {
        return itemsDao.queryMaxId();
    }

    @Override
    public void updateItem(Map<String,Object> param,Integer itemId) {

        Items items = new Items();
        try {
            items = WebUtils.copyParamToBean(param,items);
        } catch (Exception e) {
            TransactionStatus.setStatus(-1);
            logger.warning(String.format("错误为{%s},参数为{%s}，该商品为{%s}",Constant.PARAM_NO_MATCH,param.toString(),items.toString()));
        }
        items.setId(itemId);
        //更新商品
        itemsDao.updateItem(items);
        //原来的
        List<PriceSize> priceSizeByItemId = priceSizeDao.queryPriceSizeByItemId(itemId);
        //从不是套餐变成了套餐
        if(priceSizeByItemId.size() == Constant.THREE && param.get(Constant.PRICE) != null  ) {
            //删掉原来的
            for (int i = 0; i < priceSizeByItemId.size(); i++) {
                priceSizeDao.deletePriceSizeById(priceSizeByItemId.get(i).getId());
            }
            //增加新的
            priceSizeDao.addPriceSize(new PriceSize(null,null,new BigDecimal((String) param.get(Constant.PRICE)),itemId));
        }
        //从是套餐 变成 还是 套餐
        else if(priceSizeByItemId.size() == 1 && param.get(Constant.PRICE) != null) {
            priceSizeDao.updatePriceSize(new PriceSize(priceSizeByItemId.get(0).getId(),null, new BigDecimal((String) param.get(Constant.PRICE)),itemId));
        }
        //从是套餐 变成 不是套餐
        else if(param.get(Constant.PRICE) == null && priceSizeByItemId.size() == 1) {
            priceSizeDao.deletePriceSizeById(priceSizeByItemId.get(0).getId());
            addPriceSize(param,itemId);
        }
        //从不是套餐 变成 不是套餐
        else {
            priceSizeDao.updatePriceSize(new PriceSize(priceSizeByItemId.get(0).getId(),"大份",new BigDecimal((String) param.get("bigPrice")),itemId));
            priceSizeDao.updatePriceSize(new PriceSize(priceSizeByItemId.get(1).getId(),"中份",new BigDecimal((String) param.get("midPrice")),itemId));
            priceSizeDao.updatePriceSize(new PriceSize(priceSizeByItemId.get(2).getId(),"小份",new BigDecimal((String) param.get("smallPrice")),itemId));
        }
    }

    @Override
    public void deleteItemByItemId(Integer itemId) {
        itemsDao.deleteItem(itemId);
        //删除属性
        List<PriceSize> priceSizes = priceSizeDao.queryPriceSizeByItemId(itemId);

        if(priceSizes == null) {
            logger.warning(String.format("错误信息为{%s}",Constant.ITEM_WITHOUT_PRICE));
            throw new ItemException(Constant.ITEM_WITHOUT_PRICE);
        }

        priceSizes.forEach(priceSize -> {
            priceSizeDao.deletePriceSizeById(priceSize.getId());
        });

        //删除与所有店的关系
        storeItemDao.deleteStoreItemByItemId(itemId);
        //删除商品
        itemsDao.deleteItem(itemId);
    }

    @Override
    public Page<Product> queryProductPage(Integer pageNo,List<Integer> itemIdList) {
        Page<Product> productPage = new Page<>();
        //设置当前页码
        productPage.setPageNo(pageNo);
        //设置一页大小
        productPage.setPageSize(Constant.PAGE_SIZE);
        int pageTotalCount = itemIdList.size();
        //设置总记录数
        productPage.setPageTotalCount(pageTotalCount);
        //总页码小于 一页的数量
        Integer pageTotal = pageTotalCount/Constant.PAGE_SIZE;
        if(pageTotalCount % Constant.PAGE_SIZE != 0) {
            pageTotal+=1;
        }
        //设置总页数
        productPage.setPageTotal(pageTotal);

        ArrayList<Product> products = new ArrayList<>();
        System.out.println(itemIdList);

        for (int i = (pageNo-1)*Constant.PAGE_SIZE; i < pageNo*Constant.PAGE_SIZE && i < pageTotalCount; i++) {
            Integer itemId = itemIdList.get(i);
            Items item = itemsDao.queryItemByItemId(itemId);
            List<PriceSize> priceSizes = priceSizeDao.queryPriceSizeByItemId(itemId);
            products.add(new Product(item,priceSizes));
        }

        productPage.setItems(products);
        return productPage;
    }


    /**
     * 增加样式
     * @param param
     * @param itemId
     */
    private void addPriceSize(Map<String,Object> param,Integer itemId) {
        priceSizeDao.addPriceSize(new PriceSize(null,"大份", new BigDecimal((String) param.get("bigPrice")) ,itemId));
        priceSizeDao.addPriceSize(new PriceSize(null,"中份", new BigDecimal((String) param.get("midPrice")),itemId));
        priceSizeDao.addPriceSize(new PriceSize(null,"小份", new BigDecimal((String) param.get("smallPrice")),itemId));
    }
}
