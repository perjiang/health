package com.sczy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sczy.constant.MessageConstant;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.entity.Result;
import com.sczy.service.userService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class userController {

    @Reference
    private userService userService;
    User user1 =null;
    @RequestMapping("/getUsername")
    public Result getUsername(){
        //当Spring security完成认证后，会将当前用户信息保存到框架提供的上下文对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        if(user != null){
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        }

        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }
    /*
     * 查询所有用户的
     *传入queryPageBean对象中
     * currentPage;//页码
     * pageSize;//每页记录数
     * queryString;//查询条件
     * */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //把参数传入service层进行处理
        PageResult pageResult =userService.findPage(queryPageBean);
        return pageResult;
    }
    /*
     * 添加用户
     *把对应的基本信息和对应的角色相关写入
     * user //对应的数据的用户的基本信息
     * roleIds//对应的用户给予的角色
     * */
    @RequestMapping("/add")
    public Result add(@RequestBody com.sczy.pojo.User user, Integer[] roleIds){
        try {
            //进行给密码加盐加密
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String s = encoder.encode(user.getPassword());
            user.setPassword(s);
            userService.add(user,roleIds);
            return  new Result(true,MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_USER_FAIL);
        }
    }

    /*
     * 从页面传入id进行查询对应的用户信息
     * 把用户信息在传入页面
     * 为了防止数据更新不及时
     * */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            //把id传入service
            com.sczy.pojo.User user= userService.findById(id);
            return new Result(true,"",user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }

    }
    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer id){
        try {
            List<Integer> roleIds=userService.findRoleIdsByUserId(id);
            System.out.println(roleIds);
            return  new Result(true,"",roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"");
        }

    }
    /*
     * 编辑用户
     *把对应的基本信息和对应的角色相关写入
     * user //对应的数据的用户的基本信息
     * roleIds//对应的用户给予的角色
     * */
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.sczy.pojo.User user,Integer[] roleIds){
        try {
            userService.edit(user,roleIds);
            return  new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.EDIT_USER_FAIL);
        }

    }
    /*
     * 判断是否看的是登录用户的账号
     * 是的话就不执行删除
     * */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            com.sczy.pojo.User username = userService.findByUsername(user1.getUsername());
            System.out.println(username.getId());
            if(id==username.getId()){
                return new Result(false,"不可以删除正在登录的账号");
            }
            userService.delete(id);
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_USER_FAIL);
        }

    }
    }

