package com.luoyixin.www.vo;

import com.luoyixin.www.po.Destination;
import com.luoyixin.www.po.Information;

/**
 * 具体送餐地址
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.vo
 * @ClassName: Delivery
 * @create 2021/5/17-10:09
 * @Version: 1.0
 */
public class Delivery {
    /**
     * 具体信息
     */
    private Information information;
    /**
     * 地址
     */
    private Destination destination;

    public Delivery() {
    }

    public Delivery(Information information, Destination destination) {
        this.information = information;
        this.destination = destination;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "information=" + information +
                ", destination=" + destination +
                '}';
    }
}
