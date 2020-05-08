package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<CheckGroup> selectByCondition(String queryString);

    void deleteCheckGroupById(Integer id);

    void deleteAssocication(Integer id);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();
}
