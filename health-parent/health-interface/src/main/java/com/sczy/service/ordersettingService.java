package com.sczy.service;

import com.sczy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface ordersettingService {
    void add(List<OrderSetting> list);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
