package com.qfedu.fmmall.service;

import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.vo.ResultVO;

import java.sql.SQLException;

public interface OrderService {

    public ResultVO addOrder(String cids, Orders order) throws SQLException;
}
