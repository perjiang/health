package com.sczy.dao;

import com.github.pagehelper.Page;
import com.sczy.pojo.Role;

import java.util.List;
import java.util.Set;

public interface roleDao {
    Set<Role> findByUserId(Integer userId);

    Page<Role> findAll(String queryString);

    void roleAdd(Role role);

    void roleMenu(Integer roleId, Integer menuId);

    void rolePermission(Integer roleId, Integer permissionId);

    void roleEdit(Role role);

    void roleDeleteMenu(Integer id);

    void roleDeletePermission(Integer id);

    Integer findCountRoleMenu();

    Integer findCountRoleUser();

    Integer findCountRolePermission();

    void roleDelete(Integer id);

    Role roleUpdateOne(Integer id);

    List<Integer> roleUpdateTwo(Integer id);

    List<Integer> roleUpdateThree(Integer id);

    List<Role> findAll4User();
}
