package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-08-21  15:17
 */
//@ResponseBody  @Controller👇
@RestController
@RequestMapping("/goods")
@Api(value = "提供商品添加、修改、删除及查询的相关接口",tags = "商品管理")
public class GoodsController {

    @PostMapping( "/add")
    public ResultVO addGoods() {return null;}


    @DeleteMapping( "/{id}")
    public ResultVO deleteGoods(@PathVariable("id") int goodsId) {
        System.out.println("------"+goodsId);
        return new ResultVO(10000,"delete success",null);
    }

    @PutMapping( "/{id}")
    public ResultVO updateGoods(@PathVariable("id") int goodsId) {return null;}

    @GetMapping( "/list")
    public ResultVO listGoods() {return null; }

    @GetMapping( "{id}")
    public ResultVO getGoods(@PathVariable("id") int goodsId){
        return null;
    }

}
