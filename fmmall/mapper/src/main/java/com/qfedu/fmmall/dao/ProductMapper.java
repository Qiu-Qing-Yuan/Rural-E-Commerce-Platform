package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.Product;
import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDAO<Product> {

    public List<ProductVO> selectRecommendProducts();

    //查询指定一级类别下销量最高的6个商品
    public List<ProductVO> selectTop6ByCategory(int cid);
}
