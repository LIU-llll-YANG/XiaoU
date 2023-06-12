package com.example.xiaou.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.example.xiaou.pojo.Order;
import com.example.xiaou.utils.BaseServlet;
import com.example.xiaou.utils.ResultVo;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

@WebServlet("/beforepay")
public class BeforePayServlet extends BaseServlet {

    protected void toPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        BeanUtils.populate(order, parameterMap);
        System.out.println("order = " + order);
        try {
            //String outTradeNo = request.getParameter("outTradeNo");
            //System.out.println("outTradeNo = " + outTradeNo);
            //String totalAmount = request.getParameter("totalAmount");
            //System.out.println("totalAmount = " + totalAmount);
            //String subject = request.getParameter("subject");
            //System.out.println("subject = " + subject);
            InputStream resourceAsStream = BeforePayServlet.class.getClassLoader().getResourceAsStream("zfbinfo.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);

            AlipayClient alipayClient = new DefaultAlipayClient(properties.getProperty("open_api_domain"),properties.getProperty("appid"),properties.getProperty("private_key"),"json","utf-8",properties.getProperty("alipay_public_key"),properties.getProperty("sign_type"));
            AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
            alipayRequest.setNotifyUrl("");
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", order.getOutTradeNo());
            bizContent.put("total_amount", Double.parseDouble(order.getTotalAmount()));
            bizContent.put("subject", order.getSubject());
            bizContent.put("body", order.getBody());

            alipayRequest.setBizContent(bizContent.toString());
            AlipayTradePrecreateResponse alipayResponse = alipayClient.execute(alipayRequest);
            String qrCode = alipayResponse.getQrCode();
            System.out.println("qrCode = " + qrCode);
            ResultVo resultVo = new ResultVo(true,"请扫码支付",qrCode);
            String s = JSON.toJSONString(resultVo);
            response.getWriter().write(s);
            request.getRequestDispatcher("/testPay?outTradeNo="+order.getOutTradeNo()).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    

    protected void method(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TestPaySuccessServlet.doGet 执行了");
        try {
            InputStream resourceAsStream = TestPaySuccessServlet.class.getClassLoader().getResourceAsStream("zfbinfo.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            AlipayClient alipayClient = new DefaultAlipayClient(properties.getProperty("open_api_domain"), properties.getProperty("appid"), properties.getProperty("private_key"), "json", "utf-8", properties.getProperty("alipay_public_key"), properties.getProperty("sign_type"));

            //String out_trade_no = "20210817010101003";
            String out_trade_no = request.getParameter("outTradeNo");
            System.out.println("out_trade_no = " + out_trade_no);
            AlipayTradeQueryRequest payRequest = new AlipayTradeQueryRequest();//创建API对应的request类
            payRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"}"); //设置业务参数
            AlipayTradeQueryResponse payResponse = null;//通过alipayClient调用API，获得对应的response类
            payResponse = alipayClient.execute(payRequest);
            String tradeNo = payResponse.getTradeNo(); //支付宝分配的交易账号 便于以后对账
            String outTradeNo = payResponse.getOutTradeNo();  //支付订单编号
            String tradeStatus = payResponse.getTradeStatus();
            while (true) {
                System.out.println("tradeStatus = " + tradeStatus);
                if (tradeStatus != null && tradeStatus.equals("TRADE_SUCCESS")) {
                    System.out.println("success");
                }
                if (tradeStatus != null && tradeStatus.equals("TRADE_CLOSED")) {
                    System.out.println("close");
                }
                if (tradeStatus != null && tradeStatus.equals("WAIT_BUYER_PAY")) {
                    System.out.println("BUYER_PAY");
                }
                try {
                    Thread.sleep(4000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("调用查询服务出错");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
