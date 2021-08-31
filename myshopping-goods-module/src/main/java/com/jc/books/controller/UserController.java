package com.jc.books.controller;

import com.jc.books.commons.Result;
import com.jc.books.model.Users;
import com.jc.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-30 22:17
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public Result addUser(@RequestBody Users user){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setUser_password(bCryptPasswordEncoder.encode(user.getUser_password()));
        try {
            userService.addUser(user);
            return Result.success("注册成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("注册失败!");
        }
    }
}
