package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.Product;
import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDAO<Product> {

    public List<ProductVO> selectRecommendProducts();

    /*查询指定一级类别下销量最高的6个商品*/
    public List<ProductVO> selectTop6ByCategory(int cid);

    /*
     *@Description: 根据三级分类ID查询商品信息
     * @param cid  三级分类id
     * @param start  起始索引
     * @param limit 查询记录数
     **/
    public List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                                     @Param("start")int start,
                                                     @Param("limit") int limit);

    public List<String> selectBrandByCategoryId(int cid);
}
