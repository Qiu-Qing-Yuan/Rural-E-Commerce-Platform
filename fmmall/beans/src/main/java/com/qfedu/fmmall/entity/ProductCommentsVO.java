package com.qfedu.fmmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommentsVO {

    @Id
    @Column(name = "comm_id")
    private String commId;


    @Column(name = "product_id")
    private String productId;


    @Column(name = "product_name")
    private String productName;


    @Column(name = "order_item_id")
    private String orderItemId;




    @Column(name = "is_anonymous")
    private Integer isAnonymous;


    @Column(name = "comm_type")
    private Integer commType;


    @Column(name = "comm_level")
    private Integer commLevel;


    @Column(name = "comm_content")
    private String commContent;


    @Column(name = "comm_imgs")
    private String commImgs;


    @Column(name = "sepc_name")
    private Date sepcName;


    @Column(name = "reply_status")
    private Integer replyStatus;

    @Column(name = "reply_content")
    private String replyContent;


    @Column(name = "reply_time")
    private Date replyTime;


    @Column(name = "is_show")
    private Integer isShow;

    //封装评论对应的数据
    @Column(name = "user_id")
    private String userId;
    private String username;
    private String nickname;
    private String userImg;
}
