package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.OrderItemMapper;
import com.qfedu.fmmall.dao.OrdersMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.OrderItem;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.entity.ProductSku;
import com.qfedu.fmmall.entity.ShoppingCartVO;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-16  22:50
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    /*
      保存订单业务：
     * @param userId 1(zhansgan) 6(hanmeimei)
     * @param "1,8"
     * @param addrId 2(小丽)
     * String receiverName,
         String receiverMobile,String address,
         double price,int payType,String orderRemark
     *@return:
     **/
    @Transactional//如果没有，每一次数据库操作都会单独完成，有：所有数据库操作都成功之后才会一起完成
    public Map<String,String> addOrder(String cids, Orders order) throws SQLException {
        Map<String,String> map = new HashMap<>();
        //1.校验库存：根据cids查询当前订单中关联的购物车记录详情（包括库存）
        String[] arr = cids.split(",");
        List<Integer> cidList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cidList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByCids(cidList);
        boolean f = true;
        String untitled = "";
        for (ShoppingCartVO sc : list) {
            if (Integer.parseInt(sc.getCartNum()) > sc.getSkuStock()) {
                f = false;
            }
            //获取所有商品名称，以,分割拼接成字符串
            untitled = untitled + sc.getProductName() + ",";
        }

        if (f) {
            //2.表示库存充足----保存订单
            //a.userId
            //b.untitled（✔）
            //c.收货人信息 ： 根据收货地址的addrId查询收货地址详情
            //d.总价格
            //e.支付方式
            //f.订单创建时间（✔）
            //g.订单初始状态（刚刚创建完成 应为待支付 '1'）（✔）
            order.setUntitled(untitled);
            order.setCreateTime(new Date());
            order.setStatus("1");
            //生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setOrderId(orderId);
            //保存订单
            int i = ordersMapper.insert(order);

            //3、生成商品快照（几个商品就有几个快照）
            for (ShoppingCartVO sc : list) {
                int cnum = Integer.parseInt(sc.getCartNum());
                String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductName(),
                        sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()),
                        cnum, new BigDecimal(sc.getSellPrice() * cnum), new Date(), new Date(), 0);
                int m = orderItemMapper.insert(orderItem);
            }
            //4、扣减库存：根据套餐ID修改套餐库存量
            //商品1 奥利奥小饼干    套餐ID 4  2   500
            //商品2 咪咪虾条       套餐ID  1  2   127
            for (ShoppingCartVO sc : list) {
                String skuId = sc.getSkuId();
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());

                /*1、两次操作数据库*/
                /*ProductSku productSku = productSkuMapper.selectByPrimaryKey(skuId);
                productSku.setStock(newStock);
                int k = productSkuMapper.updateByPrimaryKey(productSku);*/
                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }

            //5、删除购物车：当购物车中的记录购买成功之后，购物车中对应做删除操作
            for(int cid: cidList){
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }
//            return new ResultVO(ResStatus.OK, "下单成功！", orderId);
            map.put("orderId",orderId);
            map.put("productNames",untitled);
            return map;
        } else {
//            return new ResultVO(ResStatus.NO, "库存不足，下单失败！", null);
            return null;
        }
    }

    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    @Override
    public ResultVO getOrderById(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        return new ResultVO(ResStatus.OK,"success",order.getStatus());
    }
}
