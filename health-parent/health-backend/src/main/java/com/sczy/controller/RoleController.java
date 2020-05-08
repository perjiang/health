package com.sczy.controller;


import com.alibaba.dubbo.config.annotation.Reference;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.entity.Result;
import com.sczy.pojo.Menu;
import com.sczy.pojo.Permission;
import com.sczy.pojo.Role;
import com.sczy.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;





import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;





@RestController
@RequestMapping("/role")
public class RoleController {


    @Reference
    RoleService roleService;

    /**
     * 查询出所有角色
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = roleService.findAll(queryPageBean);
        return pageResult;

    }


    /**
     * 查询所有套餐和权限
     *
     * @return
     */
    @RequestMapping("/findAllMenu")
    public Result findAllMenu() {
        try {
            List<Menu> menu = roleService.findAllMenu();
            return new Result(true, "查询所有菜单成功", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询所有菜单失败");
        }
    }

    @RequestMapping("/findAllPermission")
    public Result findAllPermission() {
        try {
            List<Permission> permission = roleService.findAllPermission();
            return new Result(true, "查询所有权限成功", permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询所有权限失败");
        }
    }

    /**
     * 添加角色
     */
    @RequestMapping("/roleAdd")
    public Result roleAdd(@RequestBody Role role, Integer[] menuIds, Integer[] permissionIds) {
        try {
            roleService.roleAdd(role, menuIds, permissionIds);
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加角色失败");
        }
    }

    /**
     * 回显formData
     */
    @RequestMapping("/roleUpdateOne")
    public Result roleUpdateOne(Integer id) {
        try {
            Role role = roleService.roleUpdateOne(id);
            return new Result(true, "回显formData成功", role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "回显formData失败");
        }
    }

    /**
     * 回显MenuIds
     */
    @RequestMapping("/roleUpdateTwo")
    public Result roleUpdateTwo(Integer id) {
        try {
            List<Integer> MenuIds = roleService.roleUpdateTwo(id);
            return new Result(true, "回显MenuIds成功", MenuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "回显MenuIds失败");
        }
    }

    /**
     * 回显PermissionIds
     */
    @RequestMapping("/roleUpdateThree")
    public Result roleUpdateThree(Integer id) {
        try {
            List<Integer> PermissionIds = roleService.roleUpdateThree(id);
            return new Result(true, "回显PermissionIds成功", PermissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "回显PermissionIds失败");
        }
    }

    /**
     * 更新角色
     */
    @RequestMapping("/roleEdit")
    public Result roleEdit(@RequestBody Role role, Integer[] menuIds, Integer[] permissionIds) {
        try {
            roleService.roleEdit(role, menuIds, permissionIds);
            return new Result(true, "更新角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新角色失败");
        }
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/roleDelete")
    public Result roleDelete(Integer id) {
        try {
            roleService.roleDelete(id);
            return new Result(true, "删除角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除角色失败");
        }

    }

    //查询所有的角色信息
    @RequestMapping("/findAll")
    public Result findAll() {
        //获取对应的全部角色信息
        List<Role> roleList = roleService.findAll();
        return new Result(true, "", roleList);
    }
}
