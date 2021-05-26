package com.luoyixin.www.vo;

import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.po
 * @ClassName: Page
 * @create 2021/5/17-22:32
 * @Version: 1.0
 */
public class Page<T> {
    /**
     * 当前页面
     */
    private Integer pageNo;
    /**
     * 总页码
     */
    private Integer pageTotal;
    /**
     * 一页多少数据
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Integer pageTotalCount;
    /**
     * 当页数据
     */
    private List<T> items;


    public Page() {
    }

    public Page(Integer pageNo, Integer pageTotal, Integer pageSize, Integer pageTotalCount, List<T> items) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                '}';
    }
}

