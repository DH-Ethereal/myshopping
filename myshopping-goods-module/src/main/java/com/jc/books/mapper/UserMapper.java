package com.jc.books.mapper;

import com.jc.books.model.UserInfo;
import com.jc.books.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-28 18:35
 **/
@Repository
public interface UserMapper {

    @Select("select user_id,user_name,user_password,user_status from tbl_user where user_name=#{username}")
    public UserInfo getUserByUserName(String userName);

    @Insert("insert into tbl_user values(default,#{user_name},#{user_password},#{user_email},#{user_birthday},#{user_hobbys},#{user_sex},#{user_address},default)")
    public void addUser(Users user);
}
