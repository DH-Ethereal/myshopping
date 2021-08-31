package com.jc.books.model;

import lombok.Data;

import java.util.Date;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:37
 **/
@Data
public class Orders {
    private String order_id;
    private Integer user_id;
    private String order_to_name;
    private String order_to_address;
    private String order_to_phone;
    private Date order_time;
    private Integer order_status;
    private Double order_total_price;
    private Integer[] book_ids;
}
