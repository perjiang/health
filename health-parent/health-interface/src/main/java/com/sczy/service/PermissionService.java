package com.sczy.service;


import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Permission;

import java.util.List;

public interface PermissionService {
    PageResult findAll(QueryPageBean queryPageBean);

    void permissionAdd(Permission permission);

    Permission permissionUpdate(Integer id);

    void permissionEdit(Permission permission);

    void permissionDelete(Integer id);
}
