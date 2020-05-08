package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.pojo.CheckGroup;
import com.sczy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface setmealDao {
    void setSetmealAndCheckGroup(Map map);

    void add(Setmeal setmeal);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();
}
