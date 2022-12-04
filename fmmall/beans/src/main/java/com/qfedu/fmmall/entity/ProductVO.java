package com.qfedu.fmmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVO {

    private String productId;
    private String productName;
    private Integer categoryId;
    private Integer rootCategoryId;
    private Integer soldNum;
    private Integer productStatus;
    private Date createTime;
    private Date updateTime;
    private String content;
    private List<ProductImg> imgs; //查询商品时关联查询商品图片
    private List<ProductSku> skus; //在查询商品的时候关联查询商品套餐信息

}
