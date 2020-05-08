package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sczy.dao.MemberDao;
import com.sczy.dao.OrderDao;
import com.sczy.service.reportService;
import com.sczy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = reportService.class)
@Transactional
public class reportServiceimpl implements reportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //获得本周一日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获得本月第一天日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //今天日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        //当天新增会员人数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        //热门套餐查询
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);
        return result;
    }
    @Override
    public List<Map<String, Object>> reportAgeAndSex() {
        List<Map<String, Object>> objects = new ArrayList<>();

        Map<String, Object> stringMan = new HashMap<>();
        Integer max =  memberDao.reportAgeAndSexman();
        stringMan.put("value",max);
        stringMan.put("name","男");
        objects.add(stringMan);

        Map<String, Object> stringWoman = new HashMap<>();
        Integer woman =  memberDao.reportAgeAndSexwoman();
        stringWoman.put("value",woman);
        stringWoman.put("name","女");
        objects.add(stringWoman);
        return objects;
    }

    @Override
    public List<Map<String, Object>> AgeBand() throws ParseException {
        List<Map<String, Object>> objects = new ArrayList<>();
        Map<String, Object> one = new HashMap<>();
        one.put("value",AgeBandFunction(0, 18));
        one.put("name","0-18");
        objects.add(one);

        Map<String, Object> tow = new HashMap<>();
        tow.put("value",AgeBandFunction(18, 30));
        tow.put("name","18-30");
        objects.add(tow);

        Map<String, Object> three = new HashMap<>();
        three.put("value",AgeBandFunction(30, 45));
        three.put("name","30-45");
        objects.add(three);

        Map<String, Object> may = new HashMap<>();
        may.put("value",AgeBandFunction(45, null));
        may.put("name","45以上");
        objects.add(may);
        return objects;



    }

    public Integer AgeBandFunction(Integer Intstart,Integer Intstop) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String substring = format.substring(4);//-02-21
        String newYear = format.substring(0,4);//2020
        Integer count =0;
        Date start = null;
        Date stop = null;


        start = simpleDateFormat.parse(Integer.toString(Integer.parseInt(newYear)-Intstart)+substring);
        if(Intstop != null){
            stop = simpleDateFormat.parse(Integer.toString(Integer.parseInt(newYear)-Intstop)+substring);
            count = memberDao.AgeBandFunction(start,stop);
        }else{
            count = memberDao.AgeBandFunctionNull(start);
        }
        return count;
    }
}
