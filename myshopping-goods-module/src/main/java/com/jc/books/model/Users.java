package com.jc.books.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: myshopping-project
 * @description: 用户信息
 * @author: hjc
 * @create: 2021-08-30 22:08
 **/
@Data
public class Users {
    private String user_name;
    private String user_password;
    private String user_email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date user_birthday;
    private String user_hobbys;
    private Integer user_sex;
    private String user_address;
}
