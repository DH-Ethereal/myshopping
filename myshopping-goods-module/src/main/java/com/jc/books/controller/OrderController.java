package com.jc.books.controller;

import com.jc.books.commons.CurUser;
import com.jc.books.commons.Result;
import com.jc.books.model.Orders;
import com.jc.books.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 19:05
 **/
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody Orders orders, @SessionAttribute("curUser") CurUser curUser){
        orders.setUser_id(curUser.getUser_id());
        orderService.addOrder(orders);
        return Result.success("购买成功！");
    }
}
