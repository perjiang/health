package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sczy.dao.MenuDao;
import com.sczy.dao.permissionDao;
import com.sczy.dao.roleDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Menu;
import com.sczy.pojo.Permission;
import com.sczy.pojo.Role;
import com.sczy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    com.sczy.dao.roleDao roleDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    com.sczy.dao.permissionDao permissionDao;

    @Override
    public PageResult findAll(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> page = roleDao.findAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void roleAdd(Role role,Integer[] menuIds,Integer[] permissionIds) {
        //1.添加role
        roleDao.roleAdd(role);
        //获取role的ID
        Integer roleId = role.getId();
        //通过t_role_menu  t_role_permission 完成添加
        for (Integer menuId : menuIds) {
            roleDao.roleMenu(roleId,menuId);
        }
        for (Integer permissionId : permissionIds) {
            roleDao.rolePermission(roleId,permissionId);
        }
    }


    /**
     * 更新数据
     * @param role
     * @param menuIds
     * @param permissionIds
     */
    @Override
    public void roleEdit(Role role,Integer[] menuIds,Integer[] permissionIds) {
        roleDao.roleEdit(role);
        //先删除roleID对应的数据在重新添加
        roleDao.roleDeleteMenu(role.getId());
        roleDao.roleDeletePermission(role.getId());
        for (Integer menuId : menuIds) {
            roleDao.roleMenu(role.getId(),menuId);
        }
        for (Integer permissionId : permissionIds) {
            roleDao.rolePermission(role.getId(),permissionId);
        }
    }

    @Override
    public void roleDelete(Integer id) throws Exception {
        //判断该id是否引用其他表
        Integer RoleMenuID = roleDao.findCountRoleMenu();
        Integer RoleUserID = roleDao.findCountRoleUser();
        Integer RolePermissionID = roleDao.findCountRolePermission();
        if (!(RoleMenuID ==0&&RoleUserID==0&&RolePermissionID==0)){
            throw new Exception("要删除数据被使用,无法删除");
        }
        roleDao.roleDelete(id);
    }


    //----Start查询所有套餐和权限
    @Override
    public List<Menu> findAllMenu() {
       return menuDao.findAllMenu();
    }

    @Override
    public List<Permission> findAllPermission() {
        return permissionDao.findAllPermission();
    }
    //----end

    //回显数据start
    @Override
    public Role roleUpdateOne(Integer id) {
        return roleDao.roleUpdateOne(id);
    }

    @Override
    public List<Integer> roleUpdateTwo(Integer id) {
        return roleDao.roleUpdateTwo(id);
    }

    @Override
    public List<Integer> roleUpdateThree(Integer id) {
        return roleDao.roleUpdateThree(id);
    }
    //回显数据stop


    @Override
    public List<Role> findAll() {
      List<Role> roleList = roleDao.findAll4User();
        return roleList;
    }

}
