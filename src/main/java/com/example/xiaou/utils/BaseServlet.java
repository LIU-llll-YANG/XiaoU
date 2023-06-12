package com.offcn.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ClassName BaseServlet
 * @Description TODO
 * @Author wjd
 * @Date 2023/3/27 16:03
 * @Version 1.0
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("method"); //addStu  deleteStu
        //反射
        //字节码对象(继承BaseServlet的字节码对象)
        Class<? extends BaseServlet> aClass = this.getClass();
        try {
            //通过字节码对象和方法名 获取方法对象
            Method methodObj = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            methodObj.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
