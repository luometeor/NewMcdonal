package com.luoyixin.www.service;

import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.po.Items;
import com.luoyixin.www.vo.Page;
import com.luoyixin.www.vo.Product;

import java.util.List;
import java.util.Map;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.service
 * @ClassName: ItemsService
 * @create 2021/5/11-16:00
 * @Version: 1.0
 */
@Service("com.luoyixin.www.service.impl.ItemsServiceImpl")
public interface ItemsService {

    /**
     * 增加商品
     * @param param 商品的属性值
     * @param storeId 店id
     */
    @Transaction
    void addItem(Map<String,Object> param,Integer storeId);

    /**
     * 根据主键查询商品
     * @param itemId 商品id
     * @return
     */
    Items queryItemByItemId(Integer itemId);

    /**
     * 获取最大的id值
     * @return 没有的话为null
     */
    Integer queryMaxId();

    /**
     * 更新item
     * @param param 商品的属性值
     * @param itemId 要更新的item的id
     */
    @Transaction
    void updateItem(Map<String,Object> param,Integer itemId);

    /**
     * 根据item的id删除item
     * @param itemId item的id
     */
    @Transaction
    void deleteItemByItemId(Integer itemId);

    /**
     * 返回总页数
     * @param pageNo 当前页
     * @param itemIdList 所有的items的list
     * @return
     */
    Page<Product> queryProductPage(Integer pageNo, List<Integer> itemIdList);

}
