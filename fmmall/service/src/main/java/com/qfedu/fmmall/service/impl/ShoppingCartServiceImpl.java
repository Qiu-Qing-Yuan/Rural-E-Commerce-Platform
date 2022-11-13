package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.entity.ShoppingCartVO;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-02  15:50
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int i = shoppingCartMapper.insert(cart);
        if(i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else{
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartsByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByUserId(userId);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", list);
        return resultVO;
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId, cartNum);
        if(i>0){
            return new ResultVO(ResStatus.OK,"update success",null);
        }else{
            return new ResultVO(ResStatus.NO,"update fail",null);
        }
    }

    @Override
    public ResultVO listShoppingCartsByCids(String cids) {
        //使用tkmapper只能查询到某张表中拥有的字段，因此无法查询到商品名称、图片、单价等信息
//        Example example = new Example(ShoppingCart.class);
        String[] split = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();//Collections 和 Arrays工具类
        for (int i = 0; i < split.length; i++) {
            cartIds.add(Integer.parseInt(split[i]));
        }

        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByCids(cartIds);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", list);
        return resultVO;
    }


}
