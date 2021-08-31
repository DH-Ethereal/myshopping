package com.jc.books.controller;

import com.jc.books.commons.Result;
import com.jc.books.model.Car;
import com.jc.books.model.view.CarView;
import com.jc.books.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: myshopping-project
 * @description: 购物车
 * @author: hjc
 * @create: 2021-08-26 09:25
 **/
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    //查看购物车详情
    @PostMapping("/queryCarInfo")
    public List<CarView> queryCarInfo(@RequestBody Car[] cars){
        return carService.queryCarInfo(cars);
    }

    //添加购物车
    @PostMapping("/addCar")
    public Result addCar(@RequestBody Car car){
        try {
            carService.addCar(car);
        }catch (Exception e){
            return Result.fail("添加商品失败!");
        }
        return Result.success("已加入购物车!");
    }

    //本地移入服务端
    @PostMapping("/localAddCars")
    public Result localAddCars(@RequestBody Car[] cars){
        try {
            for (Car car : cars) {
                carService.addCar(car);
            }
        }catch (Exception e){
            return Result.fail("添加商品失败!");
        }
        return Result.success("已加入购物车!");
    }

    //读取购物车数据本人
    @GetMapping("/readCarInfo")
    public List<Car> readCarInfo(Integer user_id){
        return carService.readCarInfoForRedis(user_id);
    }

    @PutMapping("/updateCarCount")
    public Result updateCarCount(@RequestBody Car car){
        //设置用户编号
//        car.setUser_id(93);
        try {
            carService.updateCarCount(car);
        }catch (Exception e){
            return Result.fail("更新失败!");
        }
        return Result.success("更新成功!");
    }

    //删除购物车
    @DeleteMapping("/delCars")
    public Result delCars(Integer user_id, Integer... book_ids){
        carService.delCars(book_ids,user_id);
        return Result.success("删除成功!");
    }
}
