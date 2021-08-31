package com.jc.books.controller;

import com.jc.books.commons.CurUser;
import com.jc.books.commons.Result;
import com.jc.books.service.impl.SecurityUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-28 21:42
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/success")
    public Result success(HttpSession session){
        /*Enumeration<String> attributeNames = session.getAttributeNames();
        System.out.println(attributeNames+"----------");
        while (attributeNames.hasMoreElements()){
            String attrName = attributeNames.nextElement();
            System.out.println(attrName+"<==>"+session.getAttribute(attrName));
        }
        System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));*/
        //从session获得SecurityContext对象
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        //获得当前登录的主体
        SecurityUser user = (SecurityUser) securityContext.getAuthentication().getPrincipal();
        CurUser curUser = new CurUser();
        curUser.setUser_id((Integer) user.getData());
        curUser.setUser_name(user.getUsername());
        session.setAttribute("curUser",curUser);
        System.out.println(curUser);
        return Result.success("登录成功",curUser);
    }

    @RequestMapping("/fail")
    public Result fail(HttpServletRequest request){
        Object exceptionObj = request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String message = "登录失败";
        if (exceptionObj instanceof UsernameNotFoundException) {
            message = "用户名不存在";
        } else if(exceptionObj instanceof BadCredentialsException) {
            message = "密码错误";
        } else if(exceptionObj instanceof LockedException) {
            message = "该账户已被禁用，请与管理员联系";
        } else {
            message = "系统跑偏了";
        }

        return Result.fail(message);
    }
}
