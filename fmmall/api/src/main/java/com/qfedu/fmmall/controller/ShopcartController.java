package com.qfedu.fmmall.controller;

import ch.qos.logback.core.hook.ShutdownHook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.entity.Users;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,@RequestHeader("token")String token) throws JsonProcessingException {
        String s = stringRedisTemplate.boundValueOps(token).get();
        Users users = objectMapper.readValue(s, Users.class);
        System.out.println(users);
        return shoppingCartService.addShoppingCart(cart);
    }

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int", name = "userId", value = "用户ID", required = true)
    public ResultVO list(Integer userId,@RequestHeader("token")String token){
        return shoppingCartService.listShoppingCartsByUserId(userId);
    }

    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO updateNum(@PathVariable("cid") Integer cartId,
                              @PathVariable("cnum") Integer carNum,
                              @RequestHeader("token") String token ){
        return shoppingCartService.updateCartNum(cartId, carNum);
    }

    @GetMapping("/listbycids")
    @ApiImplicitParam(dataType = "String", name = "cids", value = "选择的购物车记录的ID", required = true)
    public ResultVO listByCids(String cids, @RequestHeader("token")String token){
        return shoppingCartService.listShoppingCartsByCids(cids);
    }
}

