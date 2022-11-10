package com.qfedu.fmmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-08-18  17:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ResultVO对象",description = "封装接口返回给前端的数据")
public class ResultVO {

    //响应给前端的状态码
    @ApiModelProperty(value = "响应状态码",dataType = "int")
    private int code;
    //响应给前端的提示信息
    @ApiModelProperty("响应提示信息")
    private String msg;
    //相应给前端的数据
    @ApiModelProperty("响应数据")
    private Object data;
}
