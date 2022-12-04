package com.qfedu.fmmall;

import com.qfedu.fmmall.dao.*;
import com.qfedu.fmmall.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
class ApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Test
    void contextLoads() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories2(0);
        for (CategoryVO c1:categoryVOS){
            System.out.println(c1);
            for (CategoryVO c2:c1.getCategories()){
                System.out.println("\t"+c2);
                for (CategoryVO c3:c2.getCategories()){
                    System.out.println("\t\t"+c3);
                }
            }
        }
    }

    @Test
    public void testRecommend(){
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        productVOS.forEach(System.out::println);
    }

    @Test
    public void testSelectFirstLevelCategory(){
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        categoryVOS.forEach(System.out::println);
    }

//    @Test
//    public void testSelectComments(){
//        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommentsByProductId("3");
//        productCommentsVOS.forEach(System.out::println);
//    }

    @Test
    public void testShopCart(){
        String cids = "7,8";
        String[] arr = cids.split(",");
        List<Integer> cidList = new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            cidList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByCids(cidList);
        list.forEach(System.out::println);
    }

    @Test
    public void test(){
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        //最早时间15:28 当前时间15:58
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime",time);
        List<Orders> orders = ordersMapper.selectByExample(example);
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i).getOrderId() + "\t" + orders.get(i).getCreateTime() + "\t" + orders.get(i).getStatus());
        }
    }
}
