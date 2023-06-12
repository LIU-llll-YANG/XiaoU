package com.offcn.utils;

import java.util.List;

public class PageUtils<T> {
    //    当前页
    private int currentPage;
    //    总页数
    private int totalPage;
    //    页容量
    private int pageSize;
    //    总记录数  总条数
    private int totalCount;
    //    起始索引
    private int startIndex;
    //    上一页
    private int prePage;
    //    下一页
    private int nextPage;

    //    存放数据的地方
    public List<T> list;

    //    前端需要告诉后端分页查询的信息    查询第几页  每页展示多少
    public PageUtils(String currentPage, String pageSize, int totalCount) {
//        判断  如果currentPage不是null  就可以转换为int类型 进行赋值操作
        if (currentPage == null || currentPage.length() == 0) {
            this.currentPage = 1;
        } else {
//            转换为int类型
            this.currentPage = Integer.valueOf(currentPage);
        }

        if (pageSize == null || pageSize.length() == 0) {
            this.pageSize = 10;//默认显示10页
        } else {
            this.pageSize = Integer.valueOf(pageSize);
        }

        this.totalCount = totalCount;

//        初始化参数
        initTotalPage();
        initStartIndex();
        initNextPage();
        initPrePage();
    }

    //    初始化总页数的方法
    private void initTotalPage() {
//        总页数  = 总记录数  / 页容量  ;              特殊情况 不能整除  需要除法+1
        this.totalPage = (totalCount % pageSize) == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
    }

//    初始化起始索引
    private  void initStartIndex(){
//        起始索引 = ( 当前页码 - 1 )  *  页容量
        this.startIndex =  ( currentPage - 1 ) * pageSize;
    }

//    初始化上一页
    private void initPrePage(){
//        如果当前的页码大于 1  就可以是当前页 -1
        if (currentPage >  1){
            this.prePage = currentPage - 1 ;
        }else {
            this.prePage = 1 ;
        }
    }

//    初始化下一页
    private void initNextPage(){
//        如果当前页小于最大页  就是当前页+1
        if (currentPage < totalPage){
            this.nextPage = currentPage + 1 ;
        }else {
//            说明目前是最后一页
            this.nextPage = totalPage;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "PageUtils{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", startIndex=" + startIndex +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", list=" + list +
                '}';
    }
}
