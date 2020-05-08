package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sczy.dao.permissionDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Permission;
import com.sczy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private com.sczy.dao.permissionDao permissionDao;
    /**
     * 查询所有权限并分页
     * @return
     */
    @Override
    public PageResult findAll(QueryPageBean queryPageBean) {
     Page<Permission> page =  PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //查询所有
        List<Permission> permissionList = permissionDao.findAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 添加权限
     * @param permission
     */
    @Override
    public void permissionAdd(Permission permission) {
        permissionDao.permissionAdd(permission);
    }

    /**
     * 回显权限
     * @param id
     * @return
     */
    @Override
    public Permission permissionUpdate(Integer id) {

        return permissionDao.permissionUpdate(id);
    }

    /**
     * 更新权限
     * @param permission
     */
    @Override
    public void permissionEdit(Permission permission) {
        permissionDao.permissionEdit(permission);
    }

    /**
     * 删除权限
     * @param id
     */
    @Override
    public void permissionDelete(Integer id) {
        permissionDao.permissionDelete(id);
    }
}
