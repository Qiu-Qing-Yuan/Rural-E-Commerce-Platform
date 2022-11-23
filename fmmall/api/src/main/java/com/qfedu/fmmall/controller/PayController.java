package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-20  21:01
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    /*
     * 回调接口:当用户支付成功之后，微信支付平台就会请求这个接口，将支付状态的数据传递过来
     *
     * */
    @PostMapping("/callback")
    public String paySuccess(HttpServletRequest request) throws Exception {
        System.out.println("-------callback");
//        1、接收微信支付平台传递的数据（使用request的输入流接收）
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = 1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1) {
            builder.append(new String(bs, 0, len));
        }
        String s = builder.toString();
        //使用帮助类将xml结构的字符串转换成map
        Map<String, String> map = WXPayUtil.xmlToMap(s);//标签名为key，对应的值作为value
        //忽略大小写进行比较
        if (map != null && "success".equalsIgnoreCase(map.get("result_code"))) {
            //支付成功
            //2、修改订单状态为“待发货/已支付”

            //3、响应微信支付平台(若不响应，微信平台频繁请求)
            HashMap<String, String> resp = new HashMap<>();
            resp.put("return_code","success");
            resp.put("return_msg","OK");
            resp.put("appid",map.get("appid"));
            resp.put("result_code","success");
            return WXPayUtil.mapToXml(resp);
        }
        return null;


    }
}
