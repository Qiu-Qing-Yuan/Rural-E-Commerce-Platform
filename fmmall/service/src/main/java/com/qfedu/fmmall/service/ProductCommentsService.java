package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.stereotype.Service;


public interface ProductCommentsService {

    /*
     *@Description: 根据商品id实现评论的分页查询
     * @param productId 商品ID
     * @param pageNum   查询页码
     * @param limit 每页显示条数
     **/
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit);

    public ResultVO getCommentsCountByProductId(String productId);
}
