package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-08-18  15:48
 */
@Component
public interface UserService {


    public ResultVO userRegister(String name,String pwd);

    public ResultVO checkLogin(String name, String pwd);
}
