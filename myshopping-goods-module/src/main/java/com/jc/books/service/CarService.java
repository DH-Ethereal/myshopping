package com.jc.books.service;

import com.jc.books.model.Car;
import com.jc.books.model.view.CarView;

import java.util.List;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-26 09:31
 **/
public interface CarService {
    public List<CarView> queryCarInfo(Car[] cars);

    void addCar(Car car);

    List<Car> readCarInfoForRedis(int user_id);

    void updateCarCount(Car car);

    void delCars(Integer[] book_ids, int user_id);

    List<Car> readCarInfoForRedis(int userId,Integer... book_ids);
}
