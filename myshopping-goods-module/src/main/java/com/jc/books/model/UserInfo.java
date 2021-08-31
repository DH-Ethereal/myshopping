package com.jc.books.model;

import lombok.Data;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-28 18:29
 **/
@Data
public class UserInfo {
    private Integer user_id;
    private String user_name;
    private String user_password;
    private Integer user_status;
}
