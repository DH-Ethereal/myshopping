package com.jc.books.mapper;

import com.jc.books.model.Orders;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:46
 **/
@Repository
public interface OrderMapper {
    @Insert("insert into tbl_order values(#{order_id},#{user_id},#{order_to_name},#{order_to_address},#{order_to_phone},now(),0,#{order_total_price})")
    public void addOrder(Orders orders);
}
