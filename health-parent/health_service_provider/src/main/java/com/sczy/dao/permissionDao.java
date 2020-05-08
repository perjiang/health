package com.sczy.dao;

import com.sczy.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface permissionDao {
    Set<Permission> findByRoleId(Integer roleId);

    List<Permission> findAllPermission();

    List<Permission> findAll(String queryString);

    void permissionAdd(Permission permission);

    Permission permissionUpdate(Integer id);

    void permissionEdit(Permission permission);

    void permissionDelete(Integer id);
}
