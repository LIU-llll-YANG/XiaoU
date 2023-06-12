package com.offcn.utils;

/**
 * @ClassName ResultVo  给前端统一响应数据封装工具
 * @Description TODO
 * @Author wjd
 * @Date 2023/3/27 16:09
 * @Version 1.0
 */
public class ResultVo {
    private boolean  flag;
    private String msg;
    private Object data;

    public ResultVo(){}

    public ResultVo(boolean flag, String msg, Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public ResultVo(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
