package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPay;
import com.qfedu.fmmall.config.MyPayConfig;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-18  22:03
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供订单操作相关的操作接口",tags = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders order) {
        ResultVO resultVO = null;
        try {
//            resultVO = orderService.addOrder(cids, order);
//            String orderId = orderService.addOrder(cids,order);
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            String orderId = orderInfo.get("orderId");

//            String orderId = "1231231234569";//test
            if(orderId!=null){//订单保存成功
                //设置当前订单的信息
                HashMap<String, String> data = new HashMap<>();
                data.put("body",orderInfo.get("productNames"));        //商品描述
                data.put("out_trade_no",orderId); //使用当前用户订单的编号作为当前支付交易的交易号
                data.put("fee_type","CNY");       //支付比重
                data.put("total_fee",order.getActualAmount()*100+"");     //支付金额 1分
                data.put("trade_type","NATIVE");//交易类型
                data.put("notify_url","/pay/success");      //设置支付完成时的回调方法接口

                //微信支付：申请支付链接
                WXPay wxPay = new WXPay(new MyPayConfig());
                //发送请求，获取响应
                Map<String, String> resp = wxPay.unifiedOrder(data);
                System.out.println(resp);
            }else{
                resultVO = new ResultVO(ResStatus.NO, "提交订单失败！", null);
            }
        } catch (SQLException throwables) {
            resultVO = new ResultVO(ResStatus.NO, "提交订单失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }
}
