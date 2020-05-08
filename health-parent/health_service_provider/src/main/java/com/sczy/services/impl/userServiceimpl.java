package com.sczy.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sczy.dao.permissionDao;
import com.sczy.dao.roleDao;
import com.sczy.dao.userDao;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.pojo.Permission;
import com.sczy.pojo.Role;
import com.sczy.pojo.User;
import com.sczy.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = userService.class)
@Transactional

public class userServiceimpl implements userService {
    @Autowired
    private userDao userDao;
    @Autowired
    private roleDao roleDao;
    @Autowired
    private permissionDao permissionDao;
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);//查询用户基本信息，不包含用户的角色
        if(user == null){
            return null;
        }
        Integer userId = user.getId();
        //根据用户ID查询对应的角色
        Set<Role> roles = roleDao.findByUserId(userId);
        for (Role role : roles) {
            Integer roleId = role.getId();
            //根据角色ID查询关联的权限
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);//让角色关联权限
        }
        user.setRoles(roles);//让用户关联角色
        return user;
    }
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1 调用分页组件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //2 调用CheckItemDao
        Page<User> page = userDao.findPage(queryPageBean.getQueryString());
        //3 返回PageResult


        return new PageResult(page.getTotal(),page.getResult());
    }

    /*
     *
     *把对应的基本信息和对应的角色相关写入
     * user //对应的数据的用户的基本信息
     * roleIds//对应的用户给予的角色
     * 因为saveUserAndRoleAssociation方法需要对应的Id所以，需要在sql中进行返回id
     * */
    @Override
    public void add(User user, Integer[] roleIds) {
        //添加用户并且返回对应的ID
        userDao.add(user);
        System.out.println(user);
        //给用户添加对应的角色
        if (roleIds != null && roleIds.length>0) {
            saveUserAndRoleAssociation(roleIds,user.getId());
        }


    }
    /*
     *
     * 使用id查询对应用户的数据
     * */
    @Override
    public User findById(Integer id) {
        User user=userDao.findById(id);
        return user;
    }
    /*
     * 首先把对应的数据在对应的位置进行更改条件为用户id
     * 在把用户对应的角色删除条件用户id
     * 最后添加对应的关系
     * */
    @Override
    public void edit(User user, Integer[] roleIds) {
        //添加用户并且返回对应的ID
        userDao.edit(user);
        userDao.deleteAssociation(user.getId());
        System.out.println(roleIds);
        //给用户添加对应的角色
        if (roleIds != null && roleIds.length>0) {
            saveUserAndRoleAssociation(roleIds,user.getId());
        }
    }
    /*
     * 根据用户id获取关联的角色id
     * */
    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {

        return userDao.findRoleIdsByUserId(id);
    }

    /*
     * 删除对应id的所有数据：
     * 1.对应的id的用户
     * 2.对应的角色进行删除
     * */
    @Override
    public void delete(Integer id) {
        userDao.deleteAssociation(id);
        userDao.delete(id);
    }


    /*
     *
     *把对应的基本信息和对应的角色相关写入两张表的关联
     * userId //对应的数据的用户的id,
     * roleIds//对应的用户给予的角色
     * */
    private void saveUserAndRoleAssociation(Integer[] roleIds, int userId) {
        for (Integer roleId : roleIds) {
            Map<String, Integer> map = new HashMap<String, Integer>(2);
            map.put("user_id", userId);
            map.put("role_id", roleId);
            userDao.setUserAndRole(map);
        }

    }
}
