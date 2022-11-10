package com.qfedu.fmmall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-10-30  22:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageHelper <T>{

    //总记录数
    private int count;

    //总页数
    private int pageCount;

    //分页数据
    private List<T> list;
}
