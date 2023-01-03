package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.IndexImgMapper;
import com.qfedu.fmmall.entity.IndexImg;
import com.qfedu.fmmall.service.IndexImgService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.models.auth.In;
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
 * @date 2022-09-25  2:20 PM
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Resource
    private IndexImgMapper indexImgMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = null;
        try {
            //String结构缓存轮播图信息
//        String imgsStr = stringRedisTemplate.opsForValue().get("indexImgs");
            String imgsStr = stringRedisTemplate.boundValueOps("indexImgs").get();
            if (imgsStr != null) {
                //从redis中获取到了伦博图信息
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(imgsStr, javaType);
            } else {
                //从redis中没有获取到信息，则查询数据库
                indexImgs = indexImgMapper.listIndexImgs();
                //写到redis
                stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));
                //设置redis过期时间为1天
                stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //返回数据
        if (indexImgs != null) {
            return new ResultVO(ResStatus.OK, "success", indexImgs);
        } else {
            return new ResultVO(ResStatus.NO, "FAIL", null);
        }
    }
}
