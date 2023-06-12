package com.example.xiaou.servlet;

import com.alibaba.fastjson.JSON;
import com.example.xiaou.pojo.User;
import com.example.xiaou.service.UserService;
import com.example.xiaou.service.impl.UserServiceImpl;
import com.example.xiaou.utils.BaseServlet;
import com.example.xiaou.utils.ResultVo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/beforeuser")
public class BeforeUserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    /**
     * 登录
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException ioexception
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //params.append("phone",this.phone);
        //params.append("password",this.password);
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        User user = userService.queryUserByPhoneAndPassword(phone, password);
        System.out.println("user = " + user);
        ResultVo resultVo;
        if (user != null) {
            //登录成功
            resultVo = new ResultVo(true, "登录成功");
            this.getServletContext().setAttribute("user", user);
        } else {
            //登录失败
            resultVo = new ResultVo(false, "登录失败");
        }
        response.getWriter().write(JSON.toJSONString(resultVo));
    }

    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) this.getServletContext().getAttribute("user");
        ResultVo vo = new ResultVo(true,"成功",user);
        response.getWriter().write(JSON.toJSONString(vo));
    }

    public void method(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


}
