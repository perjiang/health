package com.sczy.service;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.User;

import java.util.List;

public interface userService {
    PageResult findPage(QueryPageBean queryPageBean);
    void add(User user, Integer[] roleIds);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user, Integer[] roleIds);

    User findByUsername(String username);;

    void delete(Integer id);
}
