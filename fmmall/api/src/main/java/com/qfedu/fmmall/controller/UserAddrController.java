package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.service.UserAddrService;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-12  14:38
 */

@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接口",tags = "收货地址管理")
@RequestMapping("/useraddr")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    public ResultVO listAddr(Integer userId, @RequestHeader("token") String token){
        return  userAddrService.listAddrsByUid(userId);
    }
}
