package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.entity.Users;
import com.qfedu.fmmall.service.UserService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-08-18  18:06
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户的登录和注册接口", tags = "用户管理")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true)
    })
    @GetMapping("/login")
    public ResultVO login(@RequestParam("username") String name,
                          @RequestParam(value = "password") String pwd) {
        return userService.checkLogin(name, pwd);
    }

    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户注册账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户注册密码", required = true)
    })
    @PostMapping("/regist")
    public ResultVO regist(@RequestBody Users user) {
        return userService.userRegister(user.getUsername(),user.getPassword());
    }

    @ApiOperation("校验token是否过期接口")
    @GetMapping("/check")
    public ResultVO userTokenCheck(@RequestHeader("token")String token){
        return new ResultVO(ResStatus.OK,"success",null);
    }
}
