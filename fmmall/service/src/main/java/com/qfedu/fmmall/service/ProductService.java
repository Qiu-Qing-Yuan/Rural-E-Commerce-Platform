package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;

public interface ProductService {

    public ResultVO listRecommendProducts();

    public ResultVO getProductBasicInfo(String productId);

    public ResultVO getProductParamsById(String productId);
}
