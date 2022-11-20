package com.qfedu.fmmall.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-20  21:01
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @PostMapping("/success")
    public void success(){

    }
}
