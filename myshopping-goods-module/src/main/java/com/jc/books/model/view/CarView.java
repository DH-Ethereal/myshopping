package com.jc.books.model.view;

import lombok.Data;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-26 09:28
 **/
@Data
public class CarView {
    private Integer book_id;
    private String book_name;
    private Double book_price;
    private String book_image;
    private Integer car_count;
    private Double total;
}
