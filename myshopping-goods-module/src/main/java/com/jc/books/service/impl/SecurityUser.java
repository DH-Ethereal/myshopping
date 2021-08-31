package com.jc.books.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-28 21:12
 **/
public class SecurityUser extends User {
    private Object data;//附加数据
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Object data) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.data = data;
    }

    public Object getData(){
        return data;
    }
}
