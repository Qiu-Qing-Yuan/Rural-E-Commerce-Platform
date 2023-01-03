package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.CategoryMapper;
import com.qfedu.fmmall.entity.CategoryVO;
import com.qfedu.fmmall.service.CategoryService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-28  9:33 AM
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*查询分类列表：包含三个级别的分类（不包含商品）*/
    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVOS = null;
        try {
            //1、查询redis
            String categories = stringRedisTemplate.boundValueOps("categories").get();

            //2、判断
            if (categories != null) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVOS = objectMapper.readValue(categories, javaType);
            } else {
                //如果redis中没有类别数据，则查询数据库
                categoryVOS = categoryMapper.selectAllCategories();
                String str = objectMapper.writeValueAsString(categoryVOS);
                stringRedisTemplate.boundValueOps("categories").set(str,1, TimeUnit.DAYS);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.OK, "success", categoryVOS);
    }

    /*查询所有一级分类，同时查询当前一级分类下销量最高的6个商品（不包含类别）*/
    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        return new ResultVO(ResStatus.OK, "success", categoryVOS);
    }
}
