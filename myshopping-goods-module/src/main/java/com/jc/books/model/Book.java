package com.jc.books.model;

import lombok.Data;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:52
 **/
@Data
public class Book {
    private Integer book_id;
    private String book_name;
    private Double book_price;
    private String book_image;
    private String book_desc;
}
