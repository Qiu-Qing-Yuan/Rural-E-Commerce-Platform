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
    private ObjectMapper objectMapper;

    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = null;
        try {
            //1000个并发请求，请求轮播图
            //String结构缓存轮播图信息
//        String imgsStr = stringRedisTemplate.opsForValue().get("indexImgs");
            String imgsStr = stringRedisTemplate.boundValueOps("indexImgs").get();

            //1000个请求查询到的redis中的数据都是null
            if (imgsStr != null) {
                //从redis中获取到了伦博图信息
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(imgsStr, javaType);
            } else {
                //从redis中没有获取到信息，则查询数据库
                //1000个请求都会进入else（service类在spring容器中是单例的，1000分并发会启动1000个线程来处理，但是共用1个service实例）
                synchronized (this){//让1000个请求串行起来，排队进入
                    //第二次查询redis
                    imgsStr = stringRedisTemplate.boundValueOps("indexImgs").get();
                    if (imgsStr == null){
                        //这1000个请求中，只有第一个请求再次查询redis时依然为null
                        indexImgs = indexImgMapper.listIndexImgs();
                        if(indexImgs != null){
                            System.out.println("——————————查询数据库");
                            //写到redis
                            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));
                            //设置redis过期时间为1天
                            stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);
                        }else{
                            List<IndexImg> arr = new ArrayList<>();
                            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(arr));
                            stringRedisTemplate.boundValueOps("indexImgs").expire(10, TimeUnit.SECONDS);
                        }

                    }else {
                        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                        indexImgs = objectMapper.readValue(imgsStr, javaType);
                    }

                }
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
