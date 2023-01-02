package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.ProductImgMapper;
import com.qfedu.fmmall.dao.ProductMapper;
import com.qfedu.fmmall.dao.ProductParamsMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.entity.*;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-29  4:00 PM
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResultVO listRecommendProducts() {
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        return new ResultVO(ResStatus.OK, "success", productVOS);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO getProductBasicInfo(String productId) {


        try {
            //①、根据商品id查询redis
            String productInfo = (String) stringRedisTemplate.boundHashOps("products").get(productId);
            //②、如果redis中查询到了商品信息则直接返回给控制器
            if (productInfo != null) {
                Product product = objectMapper.readValue(productInfo, Product.class);
                return new ResultVO(ResStatus.OK, "success", product);
            } else {
                //③如果redis中没有查到商品信息，则查询数据库
                //1.商品基本信息
                Example example = new Example(Product.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId", productId);
                criteria.andEqualTo("productStatus", 1);//状态为1表示上架商品
                List<Product> products = productMapper.selectByExample(example);
                if (products.size() > 0) {
                    //④将从数据库查询到的数据写入redis
                    Product product = products.get(0);
                    String jsonStr = objectMapper.writeValueAsString(product);
                    stringRedisTemplate.boundHashOps("products").put(productId, jsonStr);
                    return new ResultVO(ResStatus.OK, "success", product);
                } else {
                    return new ResultVO(ResStatus.NO, "查询商品不存在！", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    /*          //2.商品图片
               Example example1 = new Example(ProductImg.class);
               Example.Criteria criteria1 = example1.createCriteria();
               criteria1.andEqualTo("itemId", productId);
               List<ProductImg> productsImgs = productImgMapper.selectByExample(example1);
               //3.商品套餐
               Example example2 = new Example(ProductSku.class);
               Example.Criteria criteria2 = example2.createCriteria();
               criteria2.andEqualTo("productId", productId);
               criteria2.andEqualTo("status", 1);
               List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
               HashMap<String, Object> basicInfo = new HashMap<>();
               basicInfo.put("product", products.get(0));
               basicInfo.put("productImgs", productsImgs);
               basicInfo.put("productSkus", productSkus);
               return new ResultVO(ResStatus.OK, "success", basicInfo);*/
    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if (productParams.size() > 0) {
            return new ResultVO(ResStatus.OK, "success", productParams.get(0));
        } else {
            return new ResultVO(ResStatus.NO, "此商品可能为三无产品", null);
        }
    }

    @Override
    public ResultVO getProductsByCategoryId(int categoryId, int pageNum, int limit) {
        //1、查询分页数据
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByCategoryId(categoryId, start, limit);
        //2、查询当前类别下的商品的总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId", categoryId);
        int count = productMapper.selectCountByExample(example);
        //3、计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        //3、封装返回数据
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count, pageCount, productVOS);
        return new ResultVO(ResStatus.OK, "SUCCESS", pageHelper);
    }

    @Override
    public ResultVO listBrands(int categoryId) {
        List<String> brands = productMapper.selectBrandByCategoryId(categoryId);
        return new ResultVO(ResStatus.OK, "success", brands);
    }

    @Override
    public ResultVO searchProduct(String kw, int pageNum, int limit) {
        //1、查询搜索结果
        kw = "%" + kw + "%";
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByKeyword(kw, start, limit);
        //2、查询总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName", kw);
        int count = productMapper.selectCountByExample(example);
        //3、计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        //4、封装，返回数据
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count, pageCount, productVOS);
        return new ResultVO(ResStatus.OK, "SUCCESS", pageHelper);
    }

    @Override
    public ResultVO listBrands(String kw) {
        kw = "%" + kw + "%";
        List<String> brands = productMapper.selectBrandByKeyword(kw);
        return new ResultVO(ResStatus.OK, "SUCCESS", brands);
    }


}
