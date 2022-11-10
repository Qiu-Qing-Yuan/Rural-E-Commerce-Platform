package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.entity.ShoppingCartVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {

    public List<ShoppingCartVO> selectShopcartByUserId(int userId);
}
