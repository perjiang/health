package com.sczy.service;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Menu;
import com.sczy.pojo.Permission;
import com.sczy.pojo.Role;

import java.util.List;

public interface RoleService {

    PageResult findAll(QueryPageBean queryPageBean);

    void roleAdd(Role role, Integer[] menuIds, Integer[] permissionIds);



    void roleEdit(Role role, Integer[] menuIds, Integer[] permissionIds);

    void roleDelete(Integer id) throws Exception;

    List<Menu> findAllMenu();

    List<Permission> findAllPermission();

    /**
     * 回显数据Start
     * @param id
     * @return
     */
    Role roleUpdateOne(Integer id);

    List<Integer> roleUpdateTwo(Integer id);

    List<Integer> roleUpdateThree(Integer id);
    //  * 回显数据stop

    List<Role> findAll();

}
