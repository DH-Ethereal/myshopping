package com.jc.books.model;

import lombok.Data;

import java.util.Date;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-24 18:21
 **/
@Data
public class Comment {
    private Integer comment_id;
    private Integer book_id;
    private String comment_message;
    private Date comment_time;
    private Integer user_id;
}
