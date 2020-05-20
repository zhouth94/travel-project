package cn.itcast.travel.domain;

import java.util.List;

/**
 * @author zhou
 * @create 2020/4/5
 */
public class PageBean<T> {
    private int totalPage; //总页数
    private int totalCount; //总记录数
    private int currentPage;
    private int pageSize; //每页显示的记录数
    private List<T> list; //每页显示的数据集合

    public PageBean() {
    }

    public PageBean(int totalPage, int totalCount, int currentPage, int pageSize, List<T> list) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
