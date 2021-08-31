package com.jc.books.commons;

import lombok.Data;

/**
 * @program: myshopping-project
 * @description: 分页参数表
 * @author: hjc
 * @create: 2021-08-24 17:01
 **/
@Data
public class PageParams {
    private Integer pageNum;
    private Integer pageSize;
}
