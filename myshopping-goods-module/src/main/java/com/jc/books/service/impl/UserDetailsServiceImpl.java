package com.jc.books.service.impl;

import com.jc.books.mapper.UserMapper;
import com.jc.books.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-28 20:56
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获得用户的认证信息及其他必须数据
//        System.out.println(username + "****************");
        UserInfo userInfo = userMapper.getUserByUserName(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println(userInfo);
        //设置当前用户所拥有的权限集合
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        //将认证信息设置到User对象中，并提交给SpringSecurity进行认证
        SecurityUser user = new SecurityUser(
                userInfo.getUser_name(),
                userInfo.getUser_password(),
                true, true, true,
                userInfo.getUser_status() == 0 ? true : false,
                grantedAuthorityList,
                userInfo.getUser_id()
        );
        return user;
    }
}
