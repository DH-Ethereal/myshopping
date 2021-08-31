package com.jc.books.config;

import com.jc.books.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: myshopping-project
 * @description: SpringSecurity配置类，该类必须继承WebSecurityConfigurerAdapter并重写其中的方法
 * @author: hjc
 * @create: 2021-08-28 20:53
 **/
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /***
     * 指定执行认证的逻辑
     * @throws Exception

     @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth
     .userDetailsService(userDetailsService)
     .passwordEncoder(new BCryptPasswordEncoder());
     } */

    //手动设置认证规则
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //SHA-256 +随机盐+密钥对密码进行加密。SHA系列是Hash算法，不是加密算法，使用加密算法意味着可以解密（这个与编码/解码一样），但是采用Hash处理，其过程是不可逆的。
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    //设置资源权限认证和授权

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //开启SpringSecurity的跨域支持(默认不支持跨域)
                .cors()
             .and()
                //获得要认证的请求
                .authorizeRequests()
                    .antMatchers("/comment/addComment")
                        //需要认证才能访问
                        .authenticated()
                    .antMatchers("/orders/**")
                        .authenticated()
                    .antMatchers("/**")
                        //无需认证直接访问
                        .permitAll()
             .and()
                .formLogin()
                //.loginPage("/login.vue")//不进行设置
                //指定登录控制器(由SpringSecurity提供)
                .loginProcessingUrl("/login/login")
                //配置登录成功后的控制器
                .defaultSuccessUrl("/login/success")
                .failureForwardUrl("/login/fail")
                //配置参数名
                .usernameParameter("username")
                .passwordParameter("password")
             .and()
                //禁止请求伪造
                .csrf().disable();
        super.configure(http);
    }
}
