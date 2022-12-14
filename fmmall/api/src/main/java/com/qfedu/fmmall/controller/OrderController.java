package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPay;
import com.qfedu.fmmall.config.MyPayConfig;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
                data.put("notify_url","http://39.101.77.112:8080/pay/callback");      //设置支付完成时的回调方法接口

                //微信支付：申请支付链接
                WXPay wxPay = new WXPay(new MyPayConfig());
                //发送请求，获取响应
                Map<String, String> resp = wxPay.unifiedOrder(data);
                //支付链接
                orderInfo.put("payUrl",resp.get("code_url"));
//                System.out.println(resp);
                resultVO = new ResultVO(ResStatus.OK, "提交订单成功！", orderInfo);

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



    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId,@RequestHeader("token")String token){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/list")
    @ApiOperation("订单查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(dataType = "string", name = "status", value = "订单状态", required = false),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示条数", required = true)
    })
    public ResultVO list(@RequestHeader("token")String token,
                         String userId,String status,int pageNum,int limit){
        return orderService.listOrders(userId, status, pageNum, limit);
    }

}
