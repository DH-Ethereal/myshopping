package com.jc.books.service.impl;

import com.jc.books.mapper.BookMapper;
import com.jc.books.model.Book;
import com.jc.books.model.Car;
import com.jc.books.model.view.CarView;
import com.jc.books.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-26 09:33
 **/
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private final String USERKEYPREFIX = "user_id:";
    private final String CARKEY = "cars";

    @Override
    public List<CarView> queryCarInfo(Car[] cars) {
        System.out.println(1);
        //将cars中的图书编号导出到book_ids中；
        Integer book_ids[] = new Integer[cars.length];
        int index = 0;
        for (Car car : cars) {
            book_ids[index++] = car.getBook_id();
        }

        //根据book_ids中的book_id获取图书信息
        List<Book> bookList = bookMapper.queryBooksById(book_ids);
        //创建一个存放carView集合
        List<CarView> carViewList = new ArrayList<>();
        for (Book book : bookList) {
            for (Car car : cars) {
                if (book.getBook_id().equals(car.getBook_id())) {
                    CarView carView = new CarView();
                    carView.setBook_id(book.getBook_id());
                    carView.setBook_name(book.getBook_name());
                    carView.setBook_image(book.getBook_image());
                    carView.setBook_price(book.getBook_price());
                    carView.setCar_count(car.getCar_count());
                    carView.setTotal(book.getBook_price() * car.getCar_count());
                    carViewList.add(carView);
                    break;
                }
            }
        }
        return carViewList;
    }

    @Override
    public void addCar(Car car) {
        //版本1：单个商品
//        //获得redis中的hash类型对象
//        HashOperations hashOperations = redisTemplate.opsForHash();
//        /*
//            创建hash的value集合
//            key:user_id:1
//            value:car(book_id,car_count)
//         */
//        Map<String, Map<Integer, Integer>> userCarMap = new HashMap<>();
//        //创建购物车中的购物车对象，key为book_id,value为car_count
//        Map<Integer,Integer> userSubCarMap = new HashMap<>();
//        userCarMap.put(USERKEYPREFIX+car.getUser_id(),userSubCarMap);
//        userSubCarMap.put(car.getBook_id(), car.getCar_count());
//        //向redis中添加购物车
//        hashOperations.putAll(CARKEY,userCarMap);


        /*
            版本2：添加多个商品
            判断购物车中是否存在该用户的购物车
                如果存在，判断该用户的购物车是否存在该商品
                    如果存在，更新商品数量
                    不存在，则新增
                不存在，新增用户及购物车信息
         */

        //redis中hash类型对象
        HashOperations hashOperations = redisTemplate.opsForHash();
        //生成购物车中的用户的key
        String userKey = USERKEYPREFIX + car.getUser_id();
        //购物车
        Map<String, Map<Integer, Integer>> CarMap = new HashMap<>();
        //用户购物车
        Map<Integer, Integer> userCarMap = null;
        //判断userKey所对应的购物车数据是否存在
        Boolean exists = hashOperations.hasKey(CARKEY, userKey);
        if (exists) {
            //根据userKey获得该用户的购物车数据，返回map集合
            userCarMap = (Map<Integer, Integer>) hashOperations.get(CARKEY, userKey);
            //判断购物车中是否存在该商品
//            if (userCarMap.containsKey(car.getBook_id())) {
//                //购物车存在，更改数量
//                userCarMap.put(car.getBook_id(), userCarMap.get(car.getBook_id()) + car.getCar_count());
//            } else {
//                userCarMap.put(car.getBook_id(), car.getCar_count());
//            }
            int car_count = car.getCar_count() + userCarMap.getOrDefault(car.getBook_id(),0);
            userCarMap.put(car.getBook_id(),car_count);
        } else {
            //当前不存在该用户的购物车
            userCarMap = new HashMap<>();
            //添加购物车
            userCarMap.put(car.getBook_id(), car.getCar_count());
        }
        //将用户购物车添加到购物车集合
        CarMap.put(USERKEYPREFIX + car.getUser_id(), userCarMap);
        hashOperations.putAll(CARKEY,CarMap);
    }

    @Override
    public List<Car> readCarInfoForRedis(int user_id) {
        //获得操作对象
        HashOperations hashOperations = redisTemplate.opsForHash();
        String userKey = USERKEYPREFIX + user_id;
        Map<Integer, Integer> userCarMap = (Map<Integer, Integer>) hashOperations.get(CARKEY, userKey);
        List<Car> carList = new ArrayList<>();
        userCarMap.forEach((key, value) -> {
            Car car = new Car();
            car.setBook_id(key);
            car.setCar_count(value);
            carList.add(car);
        });
        return carList;
    }

    @Override
    public List<Car> readCarInfoForRedis(int userId, Integer... book_ids) {
        //获得redis中hash类型的操作对象
        HashOperations hashOperations = redisTemplate.opsForHash();
        //根据用户编号生成key
        String userKey = USERKEYPREFIX + userId;
        //根据key从readis中读取数据
        Map<Integer, Integer> userCarMap = (Map<Integer, Integer>) hashOperations.get(CARKEY, userKey);
        List<Car> carList = new ArrayList<>();
        for (int book_id : book_ids) {
            Integer car_count = userCarMap.get(book_id);
            if (car_count != null) {
                Car car = new Car();
                car.setBook_id(book_id);
                car.setCar_count(car_count);
                carList.add(car);
            }
        }
        return carList;
    }

    @Override
    public void updateCarCount(Car car) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String userKey = USERKEYPREFIX + car.getUser_id();
        //获取购物车数据
        Map<Integer, Integer> userCarMap = (Map<Integer, Integer>) hashOperations.get(CARKEY,userKey);
        userCarMap.put(car.getBook_id(), car.getCar_count());
        //重新提交
        hashOperations.put(CARKEY,userKey,userCarMap);
    }

    @Override
    public void delCars(Integer[] book_ids, int user_id) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String userKey = USERKEYPREFIX + user_id;
        Map<Integer, Integer> userCarMap = (Map<Integer, Integer>) hashOperations.get(CARKEY,userKey);
        //循环删除
        for (Integer book_id : book_ids) {
            userCarMap.remove(book_id);
        }
        //重新提交
        hashOperations.put(CARKEY,userKey,userCarMap);
    }
}
