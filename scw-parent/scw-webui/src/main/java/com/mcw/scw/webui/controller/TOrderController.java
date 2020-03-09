package com.mcw.scw.webui.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.config.AlipayConfig;
import com.mcw.scw.webui.constant.UserConstants;
import com.mcw.scw.webui.service.TOrderServiceFeign;
import com.mcw.scw.webui.vo.req.OrderFormInfoSubmitVo;
import com.mcw.scw.webui.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.mcw.scw.webui.vo.resp.TOrder;
import com.mcw.scw.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author mcw 2019\12\20 0020-14:30
 */
@Slf4j
@Controller
public class TOrderController {

    @Autowired
    TOrderServiceFeign orderServiceFeign;

    //@ResponseBody
    @PostMapping("/order/pay")
    public String payOrder(OrderFormInfoSubmitVo vo, HttpSession session){
        log.error("表单的信息------{}",vo);

        //1.下单
        OrderInfoSubmitVo orderInfoSubmitVo = new OrderInfoSubmitVo();//提交远程的数据

        BeanUtils.copyProperties(vo,orderInfoSubmitVo);

        UserRespVo userRespVo = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);

        if(userRespVo==null){
            return "redirect:/login";
        }

        orderInfoSubmitVo.setAccessToken(userRespVo.getAccessToken());

        ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVoSession");

        if(returnPayConfirmVo==null){
            return "redirect:/login";
        }

        orderInfoSubmitVo.setProjectid(returnPayConfirmVo.getProjectId());
        orderInfoSubmitVo.setReturnid(returnPayConfirmVo.getReturnId());
        orderInfoSubmitVo.setRtncount(returnPayConfirmVo.getNum());

        AppResponse<TOrder> resp=orderServiceFeign.saveOrder(orderInfoSubmitVo);
        TOrder order = resp.getData();

        log.error("订单详情--------{}",order);

        //2.支付
//        String orderName=returnPayConfirmVo.getProjectName();
//
//        String result=payOrder(order.getOrdernum(),order.getMoney(),orderName,order.getRemark());

        return "redirect:/member/minecrowdfunding";
//        return result;//这是一个表单，返回给浏览器，并且立即提交表单，出来二维码支付页面
    }

    /*private String payOrder(String ordernum, Integer money, String orderName, String remark) {
        // 1、创建支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        // 2、创建一次支付请求
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 3、构造支付请求数据
        alipayRequest.setBizContent(
                "{\"ordernum\":\"" + ordernum + "\"," +
                "\"money\":\"" + money + "\"," +
                "\"orderName\":\"" + orderName + "\"," +
                "\"remark\":\"" + remark + "\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = "";
        try {
        // 4、请求
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        return result;// 支付跳转页的代码，一个form表单，来到扫码页
    }
*/
    //临时写在客户端，正常应该写在订单服务系统中，需要内网穿透
    @ResponseBody
    @RequestMapping("/order/updateOrderStatus")
    public String updateOrderStatus(){

//        request.getParameterMap();

        log.debug("支付宝支付完成后，异步通知结果");
        return "success";//业务完成，必须返回"success"字符串个支付宝服务器，表示交易完成
    }
}
