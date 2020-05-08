package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sczy.dao.ordersettingDao;
import com.sczy.pojo.OrderSetting;
import com.sczy.service.ordersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = ordersettingService.class)
@Transactional
public class ordersettingServiceimpl implements ordersettingService {
    @Autowired
    private ordersettingDao ordersettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        //遍历所有的shuju
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //判断日期是否已经进行了设置
                long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    //已经进行了预约设置，执行更新操作
                    ordersettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //没有进行预约设置，执行插入操作
                    ordersettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        String begin = date + "-1";//2019-6-1
        int i = date.indexOf("-");
        String year = date.substring(0, 4);
        String month = date.substring(i+1,date.length());
        int yea = Integer.parseInt(year);
        int mon = Integer.parseInt(month);
        Calendar c = Calendar.getInstance();
        c.set(yea,mon,0);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String end = null;
        if (day == 28){
            end = date + "-28";
        }else if (day == 29){
            end = date + "-29";
        }else if (day == 30){
            end = date + "-30";
        }else if (day == 31){
            end = date + "-31";
        }

        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //调用DAO，根据日期范围查询预约设置数据
        List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());//获取日期数字（几号）
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //判断是否为空
        if (orderSetting != null){
            long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if(count > 0 ){
                ordersettingDao.editNumberByOrderDate(orderSetting);
            }else {
                ordersettingDao.add(orderSetting);
            }
        }
    }
}
