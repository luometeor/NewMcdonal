package com.luoyixin.www.vo;

import com.luoyixin.www.po.Items;
import com.luoyixin.www.po.PriceSize;

import java.util.List;

/**
 * 构成真正的商品 ，又商品跟商品尺寸
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.vo
 * @ClassName: Product
 * @create 2021/5/17-16:58
 * @Version: 1.0
 */
public class Product {
    /**
     * 商品
     */
    private Items items;
    /**
     * 商品尺寸
     */
    private List<PriceSize> priceSizeList;

    public Product() {
    }

    public Product(Items items, List<PriceSize> priceSizeList) {
        this.items = items;
        this.priceSizeList = priceSizeList;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<PriceSize> getPriceSizeList() {
        return priceSizeList;
    }

    public void setPriceSizeList(List<PriceSize> priceSizeList) {
        this.priceSizeList = priceSizeList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "items=" + items +
                ", priceSizeList=" + priceSizeList +
                '}';
    }
}
