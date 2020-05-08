package com.sczy.dao;

import com.sczy.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ordersettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    OrderSetting findByOrderDate(Date parseString2Date);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
