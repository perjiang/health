package com.sczy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sczy.constant.MessageConstant;
import com.sczy.entity.PageResult;
import com.sczy.entity.QueryPageBean;
import com.sczy.entity.Result;
import com.sczy.pojo.CheckItem;
import com.sczy.service.CheckitemService;
import jdk.jfr.Percentage;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项管理
 */
@RestController
@RequestMapping("/checkitem")
public class checkitemController {
    //调用dubbo的服务
    @Reference
    private CheckitemService checkitemService;
    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkitemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //调用服务失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkitemService.pageQuery(queryPageBean);
        return pageResult;
    }
    //删除检查项
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){
        try {
            checkitemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            //调用服务失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    //根据id查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem = checkitemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑检查项
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkitemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
         return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> list =  checkitemService.findAll();
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
