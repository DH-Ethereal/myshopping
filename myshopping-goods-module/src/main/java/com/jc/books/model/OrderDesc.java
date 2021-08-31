package com.jc.books.model;

import lombok.Data;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:38
 **/
@Data
public class OrderDesc {
    private Integer desc_id;
    private String order_id;
    private Integer book_id;
    private Integer desc_count;
}
