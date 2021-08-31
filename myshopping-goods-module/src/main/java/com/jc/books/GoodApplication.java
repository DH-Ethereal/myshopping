package com.jc.books;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-22 14:35
 **/
@SpringBootApplication
@MapperScan("com.jc.books.mapper")
public class GoodApplication {
    public static void main(String[] args){
        SpringApplication.run(GoodApplication.class,args);
    }

    /**
     * @description 跨域配置
     * @return org.springframework.web.servlet.config.annotation.WebMvcConfigurer
     * @author hjc
     * CreateDate 2021/8/22 15:04
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")//哪些资源允许被跨域访问、/**表示所有
                        //允许哪些请求方法可以进行跨域访问，默认支持get和post请求，设置"*"支持所有请求(get|post|delete|put|options)
                        .allowedMethods("*")
                        //是否允许证书（cookies）
                        .allowCredentials(true)
                        //允许哪些路径可以跨域访问当前应用
                        .allowedOriginPatterns("*");
            }
        };
    }
}
