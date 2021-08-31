package com.jc.books.service.impl;

import com.jc.books.mapper.UserMapper;
import com.jc.books.model.Users;
import com.jc.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-30 22:23
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addUser(Users user) {
        userMapper.addUser(user);
    }
}
