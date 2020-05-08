package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.pojo.User;

import java.util.List;
import java.util.Map;

public interface userDao {
    User findByUsername(String username);

    Page<User> findPage(String queryString);

    void add(User user);

    User findById(Integer id);

    void edit(User user);

    void deleteAssociation(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void setUserAndRole(Map<String, Integer> map);

    void delete(Integer id);
}
