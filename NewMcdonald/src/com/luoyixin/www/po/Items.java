package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Items
 * @create 2021/5/11-15:36
 * @Version: 1.0
 */
@Entity(tableName = "items")
public class Items {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 商品名称
     */
    @Column(columnName = "item_name",alias = "itemName")
    private String itemName;
    /**
     * 要不要由加冰属性
     */
    @Column(columnName = "cool_or_not",alias = "cool")
    private Boolean cool;
    /**
     * 要不要加辣属性
     */
    @Column(columnName = "hot_or_not",alias = "hot")
    private Boolean hot;
    /**
     * 图片路径
     */
    @Column(columnName = "img_path",alias = "imgPath")
    private String imgPath;



    public Items() {
    }

    public Items(Integer id, String itemName, Boolean cool, Boolean hot, String imgPath) {
        this.id = id;
        this.itemName = itemName;
        this.cool = cool;
        this.hot = hot;
        this.imgPath = imgPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", cool=" + cool +
                ", hot=" + hot +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
