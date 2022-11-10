package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.ProductCommentsMapper;
import com.qfedu.fmmall.entity.ProductComments;
import com.qfedu.fmmall.entity.ProductCommentsVO;
import com.qfedu.fmmall.service.ProductCommentsService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-10-29  10:19
 */
@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit) {
//        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommentsByProductId(productId);
        //1.根据商品id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);

        //2.计算总页数（必须确定每页显示多少条） pageSize = limit
        int pageCount = count%limit==0? count: count/limit+1;

        //3.查询当前页的数据（因为评论中需要用户信息，因此需要连表查询---自定义）
        int start = (pageNum-1)*limit;
        List<ProductCommentsVO> list = productCommentsMapper.selectCommentsByProductId(productId, start, limit);


        return new ResultVO(ResStatus.OK,"success",new PageHelper<ProductCommentsVO>(count,pageCount,list));
    }

    @Override
    public ResultVO getCommentsCountByProductId(String productId) {
        //1.查询当前商品评价的总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);

        int total = productCommentsMapper.selectCountByExample(example);

        //2.查询好评数
        criteria.andEqualTo("commType",1);
        int praiseTotal = productCommentsMapper.selectCountByExample(example);

        //3.查询中评数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("commType",0);
        criteria1.andEqualTo("productId",productId);
        int midTotal = productCommentsMapper.selectCountByExample(example1);

        //4.查询差评数 (优化 用减法)
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType",-1);
        int badTotal = productCommentsMapper.selectCountByExample(example2);

        //5.计算好评率
        double percent = Double.parseDouble(praiseTotal+"")/ Double.parseDouble(total+"")*100;
        String percentValue = (percent+"").substring(0,(percent+"").lastIndexOf(".")+3);//前闭后开，保留两位

        HashMap<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("goodTotal",praiseTotal);
        map.put("midTotal",midTotal);
        map.put("badTotal",badTotal);
        map.put("percent",percentValue);

        return new ResultVO(ResStatus.OK, "success", map);

    }


}
