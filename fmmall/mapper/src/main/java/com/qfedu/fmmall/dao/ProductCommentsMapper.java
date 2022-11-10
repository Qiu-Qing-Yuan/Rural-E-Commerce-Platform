package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.ProductComments;
import com.qfedu.fmmall.entity.ProductCommentsVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {

    /*
     *@Description:根据商品ID分页查询商品评论信息
     *@Params:
     * @param productId 商品ID
     * @param start 起始索引
     * @param limit 查询条数
     **/
    public List<ProductCommentsVO> selectCommentsByProductId(@Param("productId") String productId,
                                                             @Param("start") int start,
                                                             @Param("limit") int limit);
}
