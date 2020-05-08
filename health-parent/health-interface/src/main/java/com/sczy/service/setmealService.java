package com.sczy.service;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface setmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Setmeal> findAll();

    List<Map<String, Object>> findSetmealCount();
}
