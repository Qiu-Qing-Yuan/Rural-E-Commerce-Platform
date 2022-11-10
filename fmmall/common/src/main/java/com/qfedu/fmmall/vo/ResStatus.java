package com.qfedu.fmmall.vo;

public class ResStatus {

    public static final int OK = 10000;
    public static final int NO = 10001;//登录了添加购物车失败

    public static final int LOGIN_SUCCESS = 20000;//认证成功
    public static final int LOGIN_FAIL_NOT = 20001;//用户未登录
    public static final int LOGIN_FAIL_OVERDUE = 20002;//用户登录失效
}
