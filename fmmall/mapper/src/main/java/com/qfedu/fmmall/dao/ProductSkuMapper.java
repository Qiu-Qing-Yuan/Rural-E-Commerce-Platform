package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.ProductSku;
import com.qfedu.fmmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {

    /*
    * 根据商品ID，查询当前商品所有套餐中价格最低的套餐*/
    public List<ProductSku> selectLowestPriceByProductId(String productId);
}
