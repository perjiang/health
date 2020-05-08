package com.sczy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sczy.constant.MessageConstant;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.entity.Result;
import com.sczy.map.MenuMap;
import com.sczy.pojo.Menu;
import com.sczy.pojo.Role;
import com.sczy.service.MenuService;
import com.sczy.service.userService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;

    @Reference
    com.sczy.service.userService userService;

    List emptyList = new ArrayList(0);


    /**
     * 查询出所有菜单
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.findAll(queryPageBean);
        return pageResult;

    }

    /**
     * 添加菜单
     */
    @RequestMapping("/menuAdd")
    public Result menuAdd(@RequestBody Menu menu) {
        try {
            menuService.menuAdd(menu);
            return new Result(true, "添加菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加菜单失败");
        }
    }

    /**
     * 更新彩蛋
     */
    @RequestMapping("/menuUpdate")
    public Result menuUpdate(Integer id) {
        try {
            Menu menu = menuService.menuUpdate(id);
            return new Result(true, "回显菜单成功", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "回显菜单失败");
        }
    }

    /**
     * 更新彩蛋
     */
    @RequestMapping("/menuEdit")
    public Result menuEdit(@RequestBody Menu menu) {
        try {
            menuService.menuEdit(menu);
            return new Result(true, "更新菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新菜单失败");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/menuDelete")
    public Result menuDelete(Integer id) {
        try {
            menuService.menuDelete(id);
            return new Result(true, "删除菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除菜单失败");
        }
    }


    // "path": "1",
    // "title": "工作台",
    // "icon": "fa-dashboard",
    // "children": []

    // "path": "/2-1",
    // "title": "会员档案",
    // "linkUrl": "member.html"
    @RequestMapping("/getMenu")
    public Result getMenu() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            com.sczy.pojo.User user_pojo = userService.findByUsername(username);
            Set<Role> roles = user_pojo.getRoles();

            // 返回的数据
            List<Map<String, Object>> menu = new ArrayList<>();

            for (Role role : roles) {
                List<Menu> parentMenu = menuService.findByRoleId(role.getId());
                for (Menu parent : parentMenu) {
                    Map<String, Object> parentMenuMap = new MenuMap();
                    parentMenuMap.put("path",parent.getPath());
                    if (!menu.contains(parentMenuMap)) {
                        parentMenuMap.put("title",parent.getName());
                        parentMenuMap.put("icon",parent.getIcon());
                        parentMenuMap.put("children",new ArrayList<>());
                        menu.add(parentMenuMap);
                    }else {
                        int i = menu.indexOf(parentMenuMap);
                        parentMenuMap = menu.get(i);
                    }

                    List<Map<String, Object>>  childrenMapList =(List<Map<String, Object>> ) parentMenuMap.get("children");
                    List<Menu> childrenList = parent.getChildren();
                    for (Menu children : childrenList) {
                        Map<String, Object> childrenMenuMap = new MenuMap();
                        childrenMenuMap.put("path",children.getPath());
                        if (!childrenMapList.contains(childrenMenuMap)) {
                            childrenMenuMap.put("title",children.getName());
                            childrenMenuMap.put("linkUrl",children.getLinkUrl());
                            childrenMapList.add(childrenMenuMap);
                        }
                    }
                }
            }

            return new Result(true, MessageConstant.GET_MENU_SUCCESS, menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }
}
