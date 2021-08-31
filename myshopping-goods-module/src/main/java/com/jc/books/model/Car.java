package com.jc.books.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-26 09:30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer book_id;
    private Integer car_count;
    private Integer user_id;
}
