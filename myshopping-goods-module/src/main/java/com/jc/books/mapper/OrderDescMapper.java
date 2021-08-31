package com.jc.books.mapper;

import com.jc.books.model.OrderDesc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:39
 **/
@Repository
public interface OrderDescMapper {
    public void addOrderDesc(@Param("orderDescList") List<OrderDesc> orderDescList);
}
