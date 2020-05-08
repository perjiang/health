package com.sczy.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.entity.Result;
import com.sczy.pojo.Permission;
import com.sczy.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    PermissionService permissionService;

    /**
     * 查询出所有权限
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = (PageResult) permissionService.findAll(queryPageBean);
        return pageResult;

    }

    /**
     * 添加权限
     */
    @RequestMapping("/permissionAdd")
    public Result permissionAdd(@RequestBody Permission permission) {
        try {
            permissionService.permissionAdd(permission);
            return new Result(true, "添加权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加权限失败");
        }
    }

    /**
     * 更新权限
     */
    @RequestMapping("/permissionUpdate")
    public Result permissionUpdate(Integer id) {
        try {
           Permission permission = permissionService.permissionUpdate(id);
            return new Result(true, "回显权限成功",permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "回显权限失败");
        }
    }

    /**
     * 更新权限
     */
    @RequestMapping("/permissionEdit")
    public Result permissionEdit(@RequestBody Permission permission) {
        try {
             permissionService.permissionEdit(permission);
            return new Result(true, "更新权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新权限失败");
        }
    }

    /**
     * 删除
      * @param id
     * @return
     */
    @RequestMapping("/permissionDelete")
    public Result permissionDelete(Integer id) {
        try {
            permissionService.permissionDelete(id);
            return new Result(true, "删除权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除权限失败");
        }
    }
}
