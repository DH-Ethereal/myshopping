package com.jc.books.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 18:36
 **/
public class GetOrderId {
    private static int num = 0;
    private static final int MAX_VALUE = 100000;

    public synchronized static String getOrderId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String nowTime = format.format(new Date());
        String orderId = nowTime + num;
        if (num > MAX_VALUE) {
            num = 0;
        }
        num++;
        return orderId;

    }
}
