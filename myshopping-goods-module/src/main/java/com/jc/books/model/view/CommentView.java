package com.jc.books.model.view;

import lombok.Data;

import java.util.Date;

/**
 * @program: myshopping-project
 * @description: 评论视图类
 * @author: hjc
 * @create: 2021-08-24 18:19
 **/
@Data
public class CommentView {
    private Integer comment_id;
    private Integer book_id;
    private String comment_message;
    private Date comment_time;
    private String user_name;
}
