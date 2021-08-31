package com.jc.books.service.impl;

import com.jc.books.commons.GetOrderId;
import com.jc.books.mapper.OrderDescMapper;
import com.jc.books.mapper.OrderMapper;
import com.jc.books.model.Car;
import com.jc.books.model.OrderDesc;
import com.jc.books.model.Orders;
import com.jc.books.service.CarService;
import com.jc.books.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:50
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CarService carService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDescMapper orderDescMapper;

    @Override
    @Transactional
    public void addOrder(Orders orders) {
        String orderId = GetOrderId.getOrderId();
        orders.setOrder_id(orderId);
        //获取要购买的商品编号
        Integer[] book_ids = orders.getBook_ids();
        List<OrderDesc> descList = getOrderDescList(orders.getUser_id(),orders.getOrder_id(),book_ids);
        orderMapper.addOrder(orders);
        orderDescMapper.addOrderDesc(descList);
    }

    //本人提交的购物车信息
    public List<OrderDesc> getOrderDescList(int userId,String orderId, Integer... book_ids){
        List<Car> cars = carService.readCarInfoForRedis(userId,book_ids);
        List<OrderDesc> orderDescs = new ArrayList<>();
        for(Car car : cars){
            OrderDesc desc = new OrderDesc();
            desc.setBook_id(car.getBook_id());
            desc.setDesc_count(car.getCar_count());
            desc.setOrder_id(orderId);
            orderDescs.add(desc);
        }
        return orderDescs;
    }
}
