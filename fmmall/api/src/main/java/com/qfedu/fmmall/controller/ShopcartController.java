package com.qfedu.fmmall.controller;

import ch.qos.logback.core.hook.ShutdownHook;
import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.utils.Base64Utils;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-18  9:53
 */
@RestController
@RequestMapping("/shopcart")
@CrossOrigin
@Api(value = "提供购物车业务相关接口", tags = "购物车管理")
public class ShopcartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,@RequestHeader("token")String token) {
        ResultVO resultVO = shoppingCartService.addShoppingCart(cart);
        return resultVO;
    }

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int", name = "userId", value = "用户ID", required = true)
    public ResultVO list(Integer userId,@RequestHeader("token")String token){
        ResultVO resultVO = shoppingCartService.listShoppingCartsByUserId(userId);
        return resultVO;
    }
}

