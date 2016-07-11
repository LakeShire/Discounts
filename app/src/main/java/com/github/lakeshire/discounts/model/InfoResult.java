package com.github.lakeshire.discounts.model;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nali on 2016/6/29.
 */
public class InfoResult {
    private int ret;
    private int total;
    private int totalPage;
    private int pageSize;
    private int pageId;
    private List<Info> infos = new ArrayList<>();

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
=======
import java.util.List;

/**
 * Created by liuhan on 16/6/12.
 */
public class InfoResult {

    private int ret;
    private String msg;
    private int total;
    private int totalPage;
    private int pageId;
    private List<Info> infos;
>>>>>>> cd1e02f87001f8e79f9b948c76054155c0c52ae1

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

<<<<<<< HEAD
=======
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

>>>>>>> cd1e02f87001f8e79f9b948c76054155c0c52ae1
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
<<<<<<< HEAD
=======

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }
>>>>>>> cd1e02f87001f8e79f9b948c76054155c0c52ae1
}
