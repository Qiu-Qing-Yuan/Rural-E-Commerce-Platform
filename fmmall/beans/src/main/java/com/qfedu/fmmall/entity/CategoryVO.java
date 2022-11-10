package com.qfedu.fmmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-26  2:36 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryVO {

    private Integer categoryId;
    private String categoryName;
    private Integer categoryLevel;
    private Integer parentId;
    private String categoryIcon;
    private String categorySlogan;
    private String categoryPic;
    private String categoryBgColor;
    //实现首页的类别显示
    private List<ProductVO> products;
    //实现首页分类商品推荐
    private List<CategoryVO> categories;


}
