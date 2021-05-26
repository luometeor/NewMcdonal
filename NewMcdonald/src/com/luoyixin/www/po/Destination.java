package com.luoyixin.www.po;

import com.luoyixin.www.orm.annotation.Column;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Destination
 * @create 2021/5/16-14:24
 * @Version: 1.0
 */
public class Destination {
    /**
     * 主键
     */
    @Column(columnName = "id",alias = "id",match = true)
    private Integer id;
    /**
     * 省
     */
    @Column(columnName = "province",alias = "province")
    private String province;
    /**
     * 城市
     */
    @Column(columnName = "city",alias = "city")
    private String city;
    /**
     * 街道
     */
    @Column(columnName = "stress",alias = "stress")
    private String stress;

    public Destination() {
    }

    public Destination(Integer id, String province, String city, String stress) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.stress = stress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", stress='" + stress + '\'' +
                '}';
    }
}
